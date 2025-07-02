#!/bin/bash

BUILD_ARTIFACTS_DIR="out"
JVM_TARGET=21
KOTLIN_VERSION="2.2"
MAIN_PACKAGE="naked"

shopt -s nullglob

# ==============================================
# === define sources
TEST_SOURCE=(
  dummy/src/main/kotlin/Main.kt
  dummy/src/main/kotlin/Additional.kt
)

FIBO_SOURCE=(
  fibo/src/main/kotlin/fibo/fibo.kt
)

MODULE_PKG_SOURCE=(
  modules/src/main/kotlin/first/Module.kt
  modules/src/main/kotlin/algebra/mtrx2.kt
)
# ==============================================

cleanBuildArtifacts() {
    echo -e "=== clean..."

    if [[ -d "$BUILD_ARTIFACTS_DIR" ]]; then
      rm -r $BUILD_ARTIFACTS_DIR
    fi
}

checkBuildDir() {
  if [[ ! -d "$BUILD_ARTIFACTS_DIR" ]]; then
    echo -e "=== $BUILD_ARTIFACTS_DIR does not exist. Create one...\n"
    mkdir $BUILD_ARTIFACTS_DIR
  fi
}

build() {
    echo -e "=== build..."
    echo -e "jvm target=$JVM_TARGET"
    echo -e "kotlin version=$KOTLIN_VERSION"
    echo -e "sources:"
    echo -e "${TEST_SOURCE[@]}"
    echo -e "${FIBO_SOURCE[@]}"
    echo -e "${MODULE_PKG_SOURCE[@]}"

    kotlinc \
        -jvm-target $JVM_TARGET \
        -language-version $KOTLIN_VERSION \
        -include-runtime \
        "${FIBO_SOURCE[@]}" \
        "${MODULE_PKG_SOURCE[@]}" \
        -d $BUILD_ARTIFACTS_DIR/fibo.jar -verbose

    kotlinc \
        -jvm-target $JVM_TARGET \
        -language-version $KOTLIN_VERSION \
        -include-runtime \
        "${TEST_SOURCE[@]}" \
        "${MODULE_PKG_SOURCE[@]}" \
        -d $BUILD_ARTIFACTS_DIR/test.jar -verbose
}

run() {
    echo -e "=== run...\n"

    if [[ ! -d "$BUILD_ARTIFACTS_DIR" ]]; then
      echo -e "=== $BUILD_ARTIFACTS_DIR does not exist. nothing to run...\n"
      exit
    fi

    java -cp $BUILD_ARTIFACTS_DIR/test.jar naked.MainKt
    java -cp $BUILD_ARTIFACTS_DIR/fibo.jar fibo.FiboKt
}

while getopts ':-:brac' VAL ; do
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
done
shift $((OPTIND -1))
