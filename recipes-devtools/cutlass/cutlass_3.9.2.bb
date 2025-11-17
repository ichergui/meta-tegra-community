DESCRIPTION = "CUDA Templates for Linear Algebra Subroutines"
HOMEPAGE = "https://github.com/NVIDIA/cutlass"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=3a191d1cab4614f6018588fab3f40222"

SRC_URI = " \
    git://github.com/NVIDIA/cutlass.git;protocol=https;branch=main;tag=v${PV} \
    file://0001-Fixups-for-cross-building-in-OE.patch \
"
SRCREV = "ad7b2f5e84fcfa124cb02b91d5bd26d238c0459e"

DEPENDS += "cuda-nvrtc cuda-cudart libcublas googletest"

COMPATIBLE_MACHINE = "(cuda)"

inherit cmake cuda

EXTRA_OECMAKE:append = " \
    -DCUDART_LIBRARY=${STAGING_DIR_HOST}/usr/local/cuda-${CUDA_VERSION}/lib/libcudart.so \
    -DCUDA_DRIVER_LIBRARY=${STAGING_DIR_HOST}/usr/local/cuda-${CUDA_VERSION}/lib/stubs/libcuda.so \
    -DNVRTC_LIBRARY=${STAGING_DIR_HOST}/usr/local/cuda-${CUDA_VERSION}/lib/libnvrtc.so \
    -DCUTLASS_NVCC_ARCHS_SUPPORTED="${@d.getVar('CUDA_ARCHITECTURES') if d.getVar('CUDA_ARCHITECTURES') else 'OFF'}" \
    -DCUTLASS_ENABLE_TESTS=OFF \
    -DCUTLASS_ENABLE_PROFILER=OFF \
    -DCUTLASS_ENABLE_TOOLS=ON \
    -DCUTLASS_ENABLE_EXAMPLES=ON \
    -DCUTLASS_INCLUDE_DIR=${S}/include \
    -DCUTLASS_TOOLS_UTIL_INCLUDE_DIR=${S}/tools/util/include \
"

do_install:append() {
    install -d ${D}${includedir}/41_fused_multi_head_attention
    cp -R --preserve=mode,links,timestamps ${S}/examples/41_fused_multi_head_attention/* ${D}${includedir}/41_fused_multi_head_attention/
}

PACKAGES += "${PN}-test"
FILES:${PN}-dev += "${includedir}/41_fused_multi_head_attention"
FILES:${PN}-test += "${prefix}/test"

SOLIBS = "*.so*"
FILES_SOLIBSDEV = ""
INSANE_SKIP:${PN} = "buildpaths"
