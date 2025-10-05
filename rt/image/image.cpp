module;

#include <iostream>
#include <print>
#include <string>
#include <filesystem>
#include <fstream>
#include <sstream>

export module image:image;

import :color;

namespace tire {

export enum class IMAGE_DEPTH { RGB = 24, RGBA = 32 };

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

    auto asPPM() const -> std::string {
        auto stream = std::stringstream{};
        const auto components = bpp_ / 8;

        // Write header.
        stream << "P3\n" << width_ << ' ' << height_ << "\n255\n";

        // Write body.
        for ( int j = 0; j < width_ * height_; j++ ) {
            size_t base = j * components;
            const auto ir = static_cast<int>( data_[base + 0] );
            const auto ig = static_cast<int>( data_[base + 1] );
            const auto ib = static_cast<int>( data_[base + 2] );

            stream << ir << ' ' << ig << ' ' << ib << '\n';
        }

        stream.flush();

        return stream.str();
    }

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

        std::ofstream file{ filePath.string(), std::ios::trunc };

        file << asPPM();

        file.close();
    }

    virtual ~Image() {
        //
        delete[] data_;
    };

protected:
    Image() = default;

    Image( int32_t width, int32_t height, const Colori& dc ) {
        height_ = height;
        width_ = width;

        // NOTE: RGB
        bpp_ = static_cast<decltype( bpp_ )>( IMAGE_DEPTH::RGB );

        const auto components = bpp_ / 8;

        data_ = new uint8_t[width_ * height_ * components];

        // Zeroed canvas color.
        // std::fill( data_, data_ + width_ * height_ * components, 0 );

        // Fill with provided default color.
        for ( int j = 0; j < width_ * height_; j++ ) {
            size_t base = j * components;
            data_[base + 0] = dc.r();
            data_[base + 1] = dc.g();
            data_[base + 2] = dc.b();
        }
    };

    explicit Image( const Image& other ) {
        bpp_ = other.bpp_;
        width_ = other.width_;
        height_ = other.height_;

        const auto components = bpp_ / 8;

        data_ = new uint8_t[width_ * height_ * components];

        std::copy( other.data_, other.data_ + width_ * height_ * components,
                   data_ );
    }

    explicit Image( Image&& other ) {
        bpp_ = std::exchange( other.bpp_, 0 );
        width_ = std::exchange( other.width_, 0 );
        height_ = std::exchange( other.height_, 0 );
        data_ = std::exchange( other.data_, nullptr );
    }

    Image& operator=( const Image& other ) noexcept {
        Image{ other }.swap( *this );
        return *this;
    }

    Image& operator=( Image&& other ) noexcept {
        Image{ std::move( other ) }.swap( *this );
        return *this;
    }

private:
    auto swap( Image& other ) noexcept -> void {
        using std::swap;
        swap( bpp_, other.bpp_ );
        swap( width_, other.width_ );
        swap( height_, other.height_ );
        swap( data_, other.data_ );
    }

protected:
    int bpp_{};
    int width_{};
    int height_{};
    uint8_t* data_{};
};

}  // namespace tire