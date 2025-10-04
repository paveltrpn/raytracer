#include <iostream>
#include <print>

import image;

int main( int argc, char** argv ) {
    std::println( "IMAGE TEST" );

    std::println( "Create canvas and write ppm file..." );

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

    return 0;
}