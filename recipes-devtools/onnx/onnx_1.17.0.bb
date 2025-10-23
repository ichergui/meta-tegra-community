SUMMARY = "Open standard for machine learning interoperability"
HOMEPAGE = "https://github.com/onnx/onnx"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

SRC_URI = " \
    git://github.com/onnx/onnx.git;protocol=https;branch=rel-${PV};tag=v${PV} \
    file://0001-Updates-for-OE-cross-builds.patch \
"
SRCREV = "b8baa8446686496da4cc8fda09f2b6fe65c2a02c"

inherit cmake python_pep517

DEPENDS += "protobuf protobuf-native"

PACKAGECONFIG ?= "python"
PACKAGECONFIG[python] = ",,python3-numpy-native python3-pybind11-native python3-protobuf-native abseil-cpp,python3-numpy python3-protobuf"

EXTRA_OECMAKE = " \
    -DBUILD_SHARED_LIBS=ON \
    -DONNX_BUILD_TESTS=OFF \
    -DONNX_BUILD_BENCHMARKS=OFF \
    -DONNX_VERIFY_PROTO3=ON \
    -DONNX_USE_PROTOBUF_SHARED_LIBS=ON \
    -DProtobuf_LIBRARY="${STAGING_LIBDIR}/libprotobuf.so" \
    -DProtobuf_INCLUDE_DIR="${STAGING_INCDIR}" \
    -DProtobuf_PROTOC_EXECUTABLE="${STAGING_BINDIR_NATIVE}/protoc" \
    -DONNX_DISABLE_STATIC_REGISTRATION=ON \
"

do_configure() {
    cmake_do_configure
}

do_compile() {
    export PROTOBUF_PROTOC_EXECUTABLE="${STAGING_BINDIR_NATIVE}/protoc"
    cmake_do_compile
    if "${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}"; then
        python_pep517_do_compile
    fi
}

do_install() {
    cmake_do_install
    if "${@bb.utils.contains('PACKAGECONFIG', 'python', 'true', 'false', d)}"; then
        python_pep517_do_install
    fi
    # remove lines with tmpdir references, they're harmful when we build against this later
    sed -i '/CMAKE_PREFIX_PATH/d' ${D}${libdir}/cmake/ONNX/ONNXConfig.cmake
    sed -i '/Protobuf_INCLUDE_DIR/d' ${D}${libdir}/cmake/ONNX/ONNXConfig.cmake
}
