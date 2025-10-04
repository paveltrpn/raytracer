module;

#include <iostream>
#include <print>

export module image:canvas;

import :image;

namespace tire {

struct Canvas final : Image {
    Canvas( int32_t width, int32_t height ) {
        height_ = height;
        width_ = width;

        // NOTE: RGB
        bpp_ = 24;

        data_ = new char[width_ * height_ * ( bpp_ / 8 )];

        // цвет холста по умолчанию
        std::fill( data_, data_ + width_ * height_ * ( bpp_ / 8 ), 0 );
        // std::memset(data, 128, width_*height_*bpp);
    }

    auto set_pen_size( int32_t size ) -> void {
        //
        penSize_ = size;
    }

    auto setPenColor( uint8_t r, uint8_t g, uint8_t b, uint8_t a ) -> void {
        penColorR_ = r;
        penColorG_ = g;
        penColorB_ = b;
        penColorB_ = a;
    }

    auto putPixel( int32_t x, int32_t y ) -> void {
        if ( ( x < 0 ) || ( y < 0 ) || ( x >= width_ ) || ( y >= height_ ) ) {
            return;
        }

        const auto offst = bpp_ / 8;

        data_[( ( x * offst ) * height_ + y * offst ) + 0] = penColorR_;
        data_[( ( x * offst ) * height_ + y * offst ) + 1] = penColorG_;
        data_[( ( x * offst ) * height_ + y * offst ) + 2] = penColorB_;
    }

private:
    int32_t penSize_{ 1 };

    uint8_t penColorR_{ 255 };
    uint8_t penColorG_{ 255 };
    uint8_t penColorB_{ 255 };
    uint8_t penColorA_{ 255 };
};

}  // namespace tire
