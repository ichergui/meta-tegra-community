SUMMARY = "NVIDIA Holoscan Sensor Bridge - Bring Your Own Sensor (BYOS) over Ethernet"
HOMEPAGE = "https://github.com/nvidia-holoscan/holoscan-sensor-bridge"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/nvidia-holoscan/holoscan-sensor-bridge.git;protocol=https;nobranch=1;tag=${PV}"
SRCREV = "903f3db376a13ff6a2d4221b388931f87f5632b5"

DEPENDS += "cuda-cudart tegra-libraries-camera tegra-libraries-nvsci holoscan-sdk curl python3-pybind11 libeigen nvtx imgui sipl"

inherit cmake cuda setuptools3-base

SRC_URI:append = " \
    file://0001-Updates-for-OE-cross-builds.patch \
    file://0002-docs-user_guide-thor-use-ip-instead-of-nmcli.patch \
    file://0003-Use-a-separate-.cu-file-for-CUDA-kernel-instead-of-h.patch \
"

EXTRA_OECMAKE:append = " \
    -DHOLOLINK_BUILD_FUSA=ON \
    -DHOLOLINK_BUILD_PYTHON=OFF \
    -DIMGUI_SOURCE_DIR=${RECIPE_SYSROOT}/opt/nvidia/imgui \
"

do_install:append() {
    rm -rf ${D}/usr/scripts
}

INSANE_SKIP:${PN} += "buildpaths"
INSANE_SKIP:${PN}-staticdev += "buildpaths"
INSANE_SKIP:${PN}-dbg += "buildpaths"
INSANE_SKIP:${PN}-dev += "buildpaths"
