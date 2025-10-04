module;

#include <iostream>
#include <print>
#include <string>
#include <filesystem>
#include <fstream>

export module image:image;

import :color;

namespace tire {

export struct Image {
    auto bpp() const -> int {
        //
        return bpp_;
    };

    auto width() const -> int {
        //
        return width_;
    };

    auto height() const -> int {
        //
        return height_;
    };

    auto data() const -> uint8_t* {
        //
        return data_;
    };

    /**
     * @brief Base class exports as simple PPM format encoded
     * as base64 string.
     */
    virtual auto asBase64() const -> std::string {
        //
        return {};
    }

    /**
     * @brief Base class exports as simple PPM format.
     */
    virtual auto writeToFile( const std::string& path ) const -> void {
        const auto filePath = std::filesystem::path{ path };

        if ( std::filesystem::exists( filePath ) ) {
            std::println( "trying to overwrite existing file \"{}\"",
                          filePath.filename().string() );
        }

        // Open file - this will create it if it doesn't exist
        // and truncate it if it does exist
        std::ofstream file( filePath.string(), std::ios::trunc );

        const auto offst = bpp_ / 8;

        file << "P3\n" << width_ << ' ' << height_ << "\n255\n";
        for ( int j = 0; j < width_ * height_; j++ ) {
            size_t base = j * 3;
            const auto ir = static_cast<int>( data_[base + 0] );
            const auto ig = static_cast<int>( data_[base + 1] );
            const auto ib = static_cast<int>( data_[base + 2] );

            file << ir << ' ' << ig << ' ' << ib << '\n';
        }
    }

    virtual ~Image() {
        //
        delete[] data_;
    };

protected:
    Image( int32_t width, int32_t height, const Colori& dc ) {
        height_ = height;
        width_ = width;

        // NOTE: RGB
        bpp_ = 24;

        const auto offst = bpp_ / 8;

        data_ = new uint8_t[width_ * height_ * offst];

        // Default canvas color.
        // std::fill( data_, data_ + width_ * height_ * offst, 0 );

        for ( int j = 0; j < width_ * height_; j++ ) {
            size_t base = j * 3;
            data_[base + 0] = dc.r();
            data_[base + 1] = dc.g();
            data_[base + 2] = dc.b();
        }
    };

protected:
    int bpp_;
    int width_;
    int height_;
    uint8_t* data_;
};

}  // namespace tire