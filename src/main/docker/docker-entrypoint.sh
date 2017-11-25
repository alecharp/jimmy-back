#!/bin/sh
###
# Copyright (C) 2017 Jimmy
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
###

JAVA_OPTS="-XX:+UseG1GC -Xms1g -Xmx1g -Djava.security.egd=file:/dev/./urandom -Djava.awt.headless=true"

if [[ $# -gt 0 ]]; then
    exec "$@"
else
    HERE="$(dirname $(readlink -f "$0"))"
    export MALLOC_ARENA_MAX=2
    exec "java" ${JAVA_OPTS} "-jar" "$HERE/jimmy-back.jar"
fi
