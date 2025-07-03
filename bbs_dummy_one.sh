#!/bin/bash

BUILD_ARTIFACTS_DIR="out"
JVM_TARGET=21
KOTLIN_VERSION="2.2"

shopt -s nullglob

# ==============================================
# === define sources
DUMMY_ONE_SOURCE=(
  dummy_one/src/main/kotlin/dummy_one/*.kt
)

MODULE_PKG_SOURCE=(
  modules/src/main/kotlin/algebra/*.kt
  modules/src/main/kotlin/canvas/*.kt
  modules/src/main/kotlin/image/*.kt
  modules/src/main/kotlin/film/*.kt
  modules/src/main/kotlin/spatial/*.kt
)

# ==============================================

cleanBuildArtifacts() {
    echo -e "=== clean ==="

    if [[ -d "$BUILD_ARTIFACTS_DIR" ]]; then
      rm -r $BUILD_ARTIFACTS_DIR
    fi
}

checkBuildDir() {
  if [[ ! -d "$BUILD_ARTIFACTS_DIR" ]]; then
    echo -e "=== $BUILD_ARTIFACTS_DIR does not exist. Create one ===\n"
    mkdir $BUILD_ARTIFACTS_DIR
  fi
}

build() {
    echo -e "=== build ===\n"
    echo -e "jvm target=$JVM_TARGET"
    echo -e "kotlin version=$KOTLIN_VERSION\n"
    echo -e "dummy_one sources: ${DUMMY_ONE_SOURCE[@]} \n"
    echo -e "module sources: ${MODULE_PKG_SOURCE[@]} \n"

    kotlinc \
        -jvm-target $JVM_TARGET \
        -language-version $KOTLIN_VERSION \
        -include-runtime \
        "${DUMMY_ONE_SOURCE[@]}" \
        "${MODULE_PKG_SOURCE[@]}" \
        -d $BUILD_ARTIFACTS_DIR/dummy_one.jar -verbose
}

run() {
    echo -e "=== run ===\n"

    if [[ ! -d "$BUILD_ARTIFACTS_DIR" ]]; then
      echo -e "=== $BUILD_ARTIFACTS_DIR does not exist. nothing to run ===\n"
      exit
    fi

    java -cp $BUILD_ARTIFACTS_DIR/dummy_one.jar dummy_one.MainKt
}

getopts ':-:brac' VAL
  case $VAL in
    b )
      checkBuildDir
      build
      exit
      ;;
#    r ) OFILE="$OPTARG" ;;
    r )
      run
      exit
      ;;
    a )
      build
      run
      exit
      ;;
    c )
      cleanBuildArtifacts
      exit
      ;;
    - )
      case $OPTARG in
        build )
          checkBuildDir
          build
          exit
          ;;
        run )
          run
          exit
          ;;
        all )
          build
          run
          exit
          ;;
        clean )
          cleanBuildArtifacts
          exit
          ;;
        * )
          echo "unknown long argument: $OPTARG"
          exit
          ;;
      esac
      ;;
  #--------------------------------------------------------
    : )
      echo "error: no argument supplied"
      ;;
    * )
      echo "error: unknown option $OPTARG"
      echo " valid options are: aov"
      ;;
  esac
shift $((OPTIND -1))
