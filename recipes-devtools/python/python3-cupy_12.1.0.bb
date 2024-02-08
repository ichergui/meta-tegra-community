SUMMARY = "NumPy/SciPy-compatible Array Library for GPU-accelerated Computing with Python"
HOMEPAGE = "https://cupy.dev/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6b47a0b47b880f6f283bbed9af6176e5"


DEPENDS += "python3-cython-native \
    python3-fastrlock \
    libcublas \
    cuda-cudart \
    cuda-profiler-api \
    libcufft \
    libcurand \ 
    libcusparse \
    cuda-nvrtc \
    cudnn \
    dlpack \
"

SRC_URI[sha256sum] = "f6d31989cdb2d96581da12822e28b102f29e254427195c2017eac327869b7320"

COMPATIBLE_MACHINE = "(tegra)"

S = "${WORKDIR}/cupy-${PV}"

inherit pypi setuptools3 cuda

RDEPENDS:${PN} = "python3-numpy"
