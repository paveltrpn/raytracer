#include <exception>
#include <print>

import image;

void foo() {
}

int main( int argc, char** argv ) {
    std::println( "IMAGE TEST" );

    // Create simple canvas and o some drawings.
    constexpr int CANVAS_WIDTH = 512;
    constexpr int CANVAS_HEIGHT = 512;
    auto foo = tire::Canvas{ CANVAS_WIDTH, CANVAS_HEIGHT };
    foo.setPenColor( 255, 0, 0, 0 );
    foo.lineBrasenham( { 0, 0 }, { CANVAS_HEIGHT, CANVAS_HEIGHT } );
    foo.setPenColor( tire::Colori{ "mediumblue" } );
    foo.circleBrasenham( { CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2 },
                         CANVAS_HEIGHT / 4 );
    foo.setPenColor( tire::Colori{ "springgreen" } );
    foo.lineWu( { CANVAS_WIDTH, 0 }, { 0, CANVAS_HEIGHT } );
    foo.writeToFile( "out.ppm" );

    // Load tga file and draw on them.
    try {
        auto tgaImage =
            tire::Tga{ "../assets/textures/PavingStones136_color.tga" };

        auto tgaCanvas = tire::Canvas{ std::move( tgaImage ) };
        tgaCanvas.setPenColor( 255, 0, 0, 0 );
        tgaCanvas.lineBrasenham( { 0, 0 }, { CANVAS_HEIGHT, CANVAS_HEIGHT } );
        tgaCanvas.setPenColor( tire::Colori{ "mediumblue" } );
        tgaCanvas.circleBrasenham( { CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2 },
                                   CANVAS_HEIGHT / 4 );
        tgaCanvas.setPenColor( tire::Colori{ "springgreen" } );
        tgaCanvas.lineWu( { CANVAS_WIDTH, 0 }, { 0, CANVAS_HEIGHT } );
        tgaCanvas.writeToFile( "tga.ppm" );

        // Emty source tga file object
        tgaImage.writeToFile( "moved_from.tga" );

    } catch ( std::exception& e ) {
        std::println( "{}", e.what() );
    }

    return 0;
}