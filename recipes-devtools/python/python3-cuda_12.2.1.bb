SUMMARY = "CUDA Python Low-level Bindings"
HOMEPAGE = "https://nvidia.github.io/cuda-python"
LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://LICENSE;md5=06eb7fc760e442991e1d467aa9d5d993"

DEPENDS = "python3-pyclibrary-native python3-cython-native python3-pyparsing-native cuda-profiler-api"

SRC_REPO = "github.com/NVIDIA/cuda-python.git;protocol=https"
SRCBRANCH = "12.2.x"
SRC_URI = " \
    git://${SRC_REPO};branch=${SRCBRANCH} \
    file://0001-OE-cross-build-fixups.patch \
"
# v12.2.1 tag
SRCREV = "e3a8ff9a8acc79057c0c2bfe80c97cfdfd146f03"

COMPATIBLE_MACHINE = "(tegra)"

S = "${WORKDIR}/git"

inherit cuda python_setuptools_build_meta

export CUDA_HOME = "${STAGING_DIR_HOST}/usr/local/cuda-${CUDA_VERSION}"
export PARALLEL_LEVEL = "${@oe.utils.cpu_count()}"
PARALLEL_VALUE[vardepvalue] = "1"
CXXFLAGS += "-I${RECIPE_SYSROOT}/usr/local/cuda-${CUDA_VERSION}/include"
CFLAGS += "-I${RECIPE_SYSROOT}/usr/local/cuda-${CUDA_VERSION}/include"
