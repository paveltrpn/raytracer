module;

#include <iostream>
#include <print>

export module image:film;

import :image;
import :color;

namespace tire {

export struct Film final : Image {
private:
};

}  // namespace tire