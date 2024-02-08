SUMMARY = "Fast, re-entrant optimistic lock implemented in Cython"
HOMEPAGE = "https://github.com/scoder/fastrlock"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=452cbb4febc674b7cc090bb3418f4377"

SRC_URI[sha256sum] = "644ec9215cf9c4df8028d8511379a15d9c1af3e16d80e47f1b6fdc6ba118356a"

COMPATIBLE_MACHINE = "(tegra)"

S = "${WORKDIR}/fastrlock-${PV}"

inherit pypi setuptools3

RDEPENDS:${PN} += "python3-cython"
