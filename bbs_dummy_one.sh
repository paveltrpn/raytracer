#!/bin/bash

BUILD_ARTIFACTS_DIR="out"

# ========= kotlin compiler options sources ====================
KOTLIN_JVM_TARGET=21
KOTLIN_LANGUAGE_VERSION=2.2
KOTLIN_API_VERSION=2.2
# KOTLIN_VERBOSE=-verbose
# ==============================================================

# ========= project name ======================
PROJECT_NAME=dummy_one
# ==============================================

shopt -s nullglob

# ========= define sources ====================
PROJECT_SOURCE=(
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

help() {
  echo -e "Options:"
  echo -e "\t-b --build: Build project, place *.class or *.jar files in out."
  echo -e "\t-r --run: Run project from *.class or *.jar files."
  echo -e "\t-a --all: Build and run."
  echo -e "\t-c --clean: Clean all build artifacts, completely delete out directory."
  echo -e "\tSet KOTLIN_VERBOSE in script source to use verbose kotlin compiler output."
}

cleanBuildArtifacts() {
    echo -e "=== clean $PROJECT_NAME ==="

    if [[ -d "$BUILD_ARTIFACTS_DIR" ]]; then
      rm -r $BUILD_ARTIFACTS_DIR/$PROJECT_NAME.jar
    fi
}

checkBuildDir() {
  if [[ ! -d "$BUILD_ARTIFACTS_DIR" ]]; then
    echo -e "=== $BUILD_ARTIFACTS_DIR does not exist. Create one ===\n"
    mkdir $BUILD_ARTIFACTS_DIR
  fi
}

build() {
    echo -e "=== build $PROJECT_NAME ===\n"
        echo -e "kotlin jvm target=$KOTLIN_JVM_TARGET"
        echo -e "kotlin compiler version=$KOTLIN_LANGUAGE_VERSION"
        echo -e "kotlin api version=$KOTLIN_API_VERSION\n"

    echo -e "dummy_one sources:"
    for item in "${PROJECT_SOURCE[@]}"
    do
      echo "$item"
    done
    echo ""

    echo -e "module sources:"
    for item in "${MODULE_PKG_SOURCE[@]}"
    do
      echo "$item"
    done
    echo ""

    echo -e "=== compile $PROJECT_NAME ===\n"
    kotlinc \
        -jvm-target $KOTLIN_JVM_TARGET \
        -language-version $KOTLIN_LANGUAGE_VERSION \
        -api-version $KOTLIN_API_VERSION \
        -include-runtime \
        $KOTLIN_VERBOSE \
        "${PROJECT_SOURCE[@]}" \
        "${MODULE_PKG_SOURCE[@]}" \
        -d $BUILD_ARTIFACTS_DIR/$PROJECT_NAME.jar
}

run() {
    echo -e "=== run $PROJECT_NAME ===\n"

    if [[ ! -d "$BUILD_ARTIFACTS_DIR" ]]; then
      echo -e "=== $BUILD_ARTIFACTS_DIR does not exist. nothing to run ===\n"
      exit
    fi

    java -cp $BUILD_ARTIFACTS_DIR/$PROJECT_NAME.jar $PROJECT_NAME.MainKt
}

getopts ':-:brach' VAL
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
    h )
      help
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
        help )
          help
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
      echo " valid options are: brach"
      ;;
  esac
shift $((OPTIND -1))
