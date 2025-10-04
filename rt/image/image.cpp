module;

#include <iostream>
#include <print>
#include <string>
#include <filesystem>

export module image:image;

namespace tire {

export struct Image {
    auto bpp() -> int {
        //
        return bpp_;
    };

    auto width() -> int {
        //
        return width_;
    };

    auto height() -> int {
        //
        return height_;
    };

    auto data() -> char* {
        //
        return data_;
    };

    /**
     * @brief Base class exports as simple PPM format encoded
     * as base64 string.
     */
    virtual auto asBase64() -> std::string {
        //
        return {};
    }

    /**
     * @brief Base class exports as simple PPM format.
     */
    virtual auto write( const std::string& path ) -> void {
        //
        //
    }

    virtual ~Image() {
        //
        delete[] data_;
    };

protected:
    Image( int32_t width, int32_t height ) {
        height_ = height;
        width_ = width;

        // NOTE: RGB
        bpp_ = 24;

        data_ = new char[width_ * height_ * ( bpp_ / 8 )];

        // Default canvas color.
        std::fill( data_, data_ + width_ * height_ * ( bpp_ / 8 ), 0 );
    };

protected:
    int bpp_;
    int width_;
    int height_;
    char* data_;
};

}  // namespace tire