//
// breakout.c
//
// Computer Science 50
// Problem Set 3
//

// standard libraries
#define _XOPEN_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// Stanford Portable Library
#include <spl/gevents.h>
#include <spl/gobjects.h>
#include <spl/gwindow.h>

// height and width of game's window in pixels
#define HEIGHT 600
#define WIDTH 400

// number of rows of bricks
#define ROWS 5

// number of columns of bricks
#define COLS 10

// radius of ball in pixels
#define RADIUS 10

// lives
#define LIVES 3

// height and width of paddle in pixels
#define HEIGHT_PADDLE 10
#define WIDTH_PADDLE 60

// prototypes
void initBricks(GWindow window);
GOval initBall(GWindow window);
GRect initPaddle(GWindow window);
GLabel initScoreboard(GWindow window);
void updateScoreboard(GWindow window, GLabel label, int points);
GObject detectCollision(GWindow window, GOval ball);

int main(void)
{
    // seed pseudorandom number generator
    srand48(time(NULL));

    // instantiate window
    GWindow window = newGWindow(WIDTH, HEIGHT);

    // instantiate bricks
    initBricks(window);

    // instantiate ball, centered in middle of window
    GOval ball = initBall(window);

    // instantiate paddle, centered at bottom of window
    GRect paddle = initPaddle(window);

    // instantiate scoreboard, centered in middle of window, just above ball
    GLabel label = initScoreboard(window);

    // number of bricks initially
    int bricks = COLS * ROWS;

    // number of lives initially
    int lives = LIVES;

    // number of points initially
    int points = 0;

    // keep playing until game over
    
    int dx = (2 * (int) (drand48() + 0.5)) - 1;
    int dy = 2;

    // need to click to start the game
    while(true)
    {
        GEvent start = getNextEvent(MOUSE_EVENT);
        if (start != NULL)
        {
            if (getEventType(start) == MOUSE_CLICKED)
            {
                break;
            }  
        }
    }
    
    // play the game until die or destroy all the bricks        
    while (lives > 0 && bricks > 0)
    {
        GEvent play = getNextEvent(MOUSE_EVENT);
        if (play != NULL)
        {
            if (getEventType(play) == MOUSE_MOVED)
            {
                double x = getX(play) - getWidth(paddle) / 2;
                double y = 0.9 * HEIGHT;
                if ((x >= 0) && (x <= WIDTH - WIDTH_PADDLE))
                {
                    setLocation(paddle, x, y);
                }
            }
        }
        
        // movement of the ball
        move(ball, dx, dy);
        if (getX(ball) + getWidth(ball) >= getWidth(window))
        {
            dx = -dx;
        }
        else if (getX(ball) <= 0)
        {
            dx = -dx;
        }
        if (getY(ball) + getHeight(ball) >= getHeight(window))
        {
            lives -= 1;
            waitForClick();                         
            setLocation(paddle, (WIDTH - WIDTH_PADDLE) / 2, 0.9 * HEIGHT);
            setLocation(ball, WIDTH / 2 - RADIUS, HEIGHT / 2 - RADIUS); 
            dx = (2 * (int) (drand48() + 0.5)) - 1;           
        }
        else if (getY(ball) <= 0)
        {
            dy = -dy;
        }
        
        // collision : bounce and (if it's a brick) destroy    
        GObject choc = detectCollision(window, ball);
        if (choc == paddle)
        {
            dy = -dy;
        }
        if ( (choc != paddle) && (choc != NULL) && (strcmp(getType(choc), "GRect") == 0))
        {
            dy = -dy;
            removeGWindow(window, choc);
            bricks = bricks - 1;
            updateScoreboard(window, label, 50 - bricks);
        }
                
        pause(10);
    }

    // message of the end
    if (lives == 0)
    {
        GLabel bad_end = newGLabel("YOU LOSE!");
        setFont(bad_end, "SansSerif-36");
        setColor(bad_end, "RED");
        double x = (getWidth(window) - getWidth(bad_end)) / 2;
        double y = 0.07 * getHeight(window);
        setLocation(bad_end, x, y);
        add(window, bad_end);
    }
    else
    {
        GLabel good_end = newGLabel("YOU WIN!");
        setFont(good_end, "SansSerif-36");
        setColor(good_end, "RED");
        double x = (getWidth(window) - getWidth(good_end)) / 2;
        double y = 0.07 * getHeight(window);
        setLocation(good_end, x, y);
        add(window, good_end);    
    }
    // wait for click before exiting    
    waitForClick();

    // game over
    closeGWindow(window);
    return 0;
}

/**
 * Initializes window with a grid of bricks.
 */
void initBricks(GWindow window)
{
    string colors[5];
    colors[0] = "BLUE";
    colors[1] = "YELLOW";
    colors[2] = "ORANGE";
    colors[3] = "MAGENTA";
    colors[4] = "GREEN";                
    
    int start = (int) (5 * drand48());
    for (int j = 0; j < ROWS; j++)
    { 
        string color = colors[(j + start) % 5];
        for (int i = 2; i < WIDTH; i = i + 40)
        {
            GRect brick = newGRect(i, 0.1 * HEIGHT + (j * 14), 36, HEIGHT_PADDLE);
            setColor(brick, color);
            setFilled(brick, true);        
            add(window, brick);
        }
    }
    
}

/**
 * Instantiates ball in center of window.  Returns ball.
 */
GOval initBall(GWindow window)
{
    GOval ball = newGOval(WIDTH / 2 - RADIUS, HEIGHT / 2 - RADIUS, 2 * RADIUS, 2 * RADIUS);
    setColor(ball, "BLACK");
    setFilled(ball, true);  
    add(window, ball);      
    return ball;
}

/**
 * Instantiates paddle in bottom-middle of window.
 */
GRect initPaddle(GWindow window)
{
    GRect paddle = newGRect((WIDTH - WIDTH_PADDLE) / 2, 0.9 * HEIGHT, WIDTH_PADDLE, HEIGHT_PADDLE);
    setColor(paddle, "BLACK");
    setFilled(paddle, true);
    add(window, paddle);
    return paddle;
}

/**
 * Instantiates, configures, and returns label for scoreboard.
 */
GLabel initScoreboard(GWindow window)
{
    GLabel label = newGLabel("0");
    setFont(label, "SansSerif-36");
    setColor(label, "GRAY");
    double x = (getWidth(window) - getWidth(label)) / 2;
    double y = (getHeight(window) - getHeight(label)) / 2;
    setLocation(label, x, y);
    add(window, label);   
    return label;
}

/**
 * Updates scoreboard's label, keeping it centered in window.
 */
void updateScoreboard(GWindow window, GLabel label, int points)
{
    // update label
    char s[12];
    sprintf(s, "%i", points);
    setLabel(label, s);

    // center label in window
    double x = (getWidth(window) - getWidth(label)) / 2;
    double y = (getHeight(window) - getHeight(label)) / 2;
    setLocation(label, x, y);
}

/**
 * Detects whether ball has collided with some object in window
 * by checking the four corners of its bounding box (which are
 * outside the ball's GOval, and so the ball can't collide with
 * itself).  Returns object if so, else NULL.
 */
GObject detectCollision(GWindow window, GOval ball)
{
    // ball's location
    double x = getX(ball);
    double y = getY(ball);

    // for checking for collisions
    GObject object;

    // check for collision at ball's top-left corner
    object = getGObjectAt(window, x, y);
    if (object != NULL)
    {
        return object;
    }

    // check for collision at ball's top-right corner
    object = getGObjectAt(window, x + 2 * RADIUS, y);
    if (object != NULL)
    {
        return object;
    }

    // check for collision at ball's bottom-left corner
    object = getGObjectAt(window, x, y + 2 * RADIUS);
    if (object != NULL)
    {
        return object;
    }

    // check for collision at ball's bottom-right corner
    object = getGObjectAt(window, x + 2 * RADIUS, y + 2 * RADIUS);
    if (object != NULL)
    {
        return object;
    }

    // no collision
    return NULL;
}
