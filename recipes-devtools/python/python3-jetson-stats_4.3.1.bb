SUMMARY = "Interactive system-monitor process viewer for NVIDIA Jetson TX1, \
  Nano, TX2, Xavier and Orin series"
HOMEPAGE = "https://pypi.org/project/jetson-stats/"
SECTION = "devel/python"
LICENSE = "AGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8763b57f0092c337eb12c354870a324a"

SRC_URI += "file://0001-fix-installation-process-issue-and-cleanup.patch \
            file://0002-fix-paths-to-match-with-OE-and-getting-the-right-ver.patch \
           "
SRC_URI[sha256sum] = "38b11fcb6a27d59a194bc587e7665bccfc0c658979171969974550013e62afeb"

COMPATIBLE_MACHINE = "(tegra)"

inherit pypi setuptools3 systemd useradd

do_install:append() {
    install -d ${D}${sysconfdir}/profile.d
    install -m 0755 ${D}${prefix}/jetson_stats/jtop_env.sh ${D}${sysconfdir}/profile.d

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${D}${prefix}/jetson_stats/jtop.service ${D}${systemd_system_unitdir}
    sed -i 's/ExecStart=\/usr\/local\/bin\/jtop/ExecStart=\/usr\/bin\/jtop/g' \
        ${D}${systemd_system_unitdir}/jtop.service

    rm ${D}${bindir}/jetson_config
    rm ${D}${bindir}/jetson_swap
    rm ${D}${bindir}/jetson_release
    rm ${D}${prefix}/jetson_stats/jtop.service
    rm ${D}${prefix}/jetson_stats/jtop_env.sh
    rm -rf ${D}${prefix}/jetson_stats
}

SYSTEMD_SERVICE:${PN} = "jtop.service"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "-r -d ${libexecdir} -M -s ${base_sbindir}/nologin -g jtop jtop"
GROUPADD_PARAM:${PN} = "-f -r jtop"

FILES:${PN} += "${sysconfdir}/profile.d"
RDEPENDS:${PN} += " \
    bash \
    python3-curses \
    python3-email \
    python3-fcntl \
    python3-json \
    python3-multiprocessing \
    python3-setuptools \
    python3-smbus2 \
    python3-distro \
    tegra-nvpmodel \
    tegra-tools-jetson-clocks \
    tegra-tools-tegrastats \
    nv-tegra-release \
"
RRECOMMENDS:${PN} += "kernel-module-nvgpu"
