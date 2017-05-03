/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Random;

import org.junit.Test;

/**
 * Test the MinesweeperServer
 */
public class MinesweeperServerTestMultiMoves {
    
    private static final String LOCALHOST = "127.0.0.1";
    private static final int PORT = 4000 + new Random().nextInt(1 << 15);

    private static final int MAX_CONNECTION_ATTEMPTS = 10;

    private static final String BOARDS_PKG = "minesweeper/boards/";

    /**
     * Start a MinesweeperServer in debug mode with a board file from BOARDS_PKG.
     * 
     * @param boardFile board to load
     * @return thread running the server
     * @throws IOException if the board file cannot be found
     */
    private static Thread startMinesweeperServer(String boardFile) throws IOException {
        final URL boardURL = ClassLoader.getSystemClassLoader().getResource(BOARDS_PKG + boardFile);
        if (boardURL == null) {
            throw new IOException("Failed to locate resource " + boardFile);
        }
        final String boardPath;
        try {
            boardPath = new File(boardURL.toURI()).getAbsolutePath();
        } catch (URISyntaxException urise) {
            throw new IOException("Invalid URL " + boardURL, urise);
        }
        final String[] args = new String[] {
                "--debug",
                "--port", Integer.toString(PORT),
                "--file", boardPath
        };
        Thread serverThread = new Thread(() -> MinesweeperServer.main(args));
        serverThread.start();
        return serverThread;
    }

    /**
     * Connect to a MinesweeperServer and return the connected socket.
     * 
     * @param server abort connection attempts if the server thread dies
     * @return socket connected to the server
     * @throws IOException if the connection fails
     */
    private static Socket connectToMinesweeperServer(Thread server) throws IOException {
        int attempts = 0;
        while (true) {
            try {
                Socket socket = new Socket(LOCALHOST, PORT);
                socket.setSoTimeout(3000);
                return socket;
            } catch (ConnectException ce) {
                if ( ! server.isAlive()) {
                    throw new IOException("Server thread not running");
                }
                if (++attempts > MAX_CONNECTION_ATTEMPTS) {
                    throw new IOException("Exceeded max connection attempts", ce);
                }
                try { Thread.sleep(attempts * 10); } catch (InterruptedException ie) { }
            }
        }
    }
    
    /**
     * test some moves (two player)
     * 
     * @throws IOException
     */
    @Test(timeout = 10000)
    public void testMultiMoves() throws IOException {

        Thread thread = startMinesweeperServer("board_file_2");

        Socket socket1 = connectToMinesweeperServer(thread);

        BufferedReader in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        PrintWriter out1 = new PrintWriter(socket1.getOutputStream(), true);

        assertTrue("expected HELLO message", in1.readLine().startsWith("Welcome"));

        out1.println("look");
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());

        out1.println("dig 3 1");
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - 1 - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        assertEquals("- - - - - - -", in1.readLine());
        
        Socket socket2 = connectToMinesweeperServer(thread);

        BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        PrintWriter out2 = new PrintWriter(socket2.getOutputStream(), true);
        assertTrue("expected HELLO message", in2.readLine().startsWith("Welcome"));
        
        out2.println("look");
        assertEquals("- - - - - - -", in2.readLine());
        assertEquals("- - - 1 - - -", in2.readLine());
        assertEquals("- - - - - - -", in2.readLine());
        assertEquals("- - - - - - -", in2.readLine());
        assertEquals("- - - - - - -", in2.readLine());
        assertEquals("- - - - - - -", in2.readLine());
        
        out2.println("dig 3 2");
        assertEquals("- - - - - - -", in2.readLine());
        assertEquals("- - - 1 - - -", in2.readLine());
        assertEquals("- - - 1 - - -", in2.readLine());
        assertEquals("- - - - - - -", in2.readLine());
        assertEquals("- - - - - - -", in2.readLine());
        assertEquals("- - - - - - -", in2.readLine());
        

        out1.println("dig 4 1");
        assertEquals("BOOM!", in1.readLine());

        out1.println("look"); // debug mode is on
        assertEquals("             ", in1.readLine());
        assertEquals("             ", in1.readLine());
        assertEquals("             ", in1.readLine());
        assertEquals("             ", in1.readLine());
        assertEquals("1 1          ", in1.readLine());
        assertEquals("- 1          ", in1.readLine());
        
        out2.println("dig 0 5");
        assertEquals("BOOM!", in2.readLine());
        
        out2.println("look"); // debug mode is on
        assertEquals("             ", in2.readLine());
        assertEquals("             ", in2.readLine());
        assertEquals("             ", in2.readLine());
        assertEquals("             ", in2.readLine());
        assertEquals("             ", in2.readLine());
        assertEquals("             ", in2.readLine());

        out1.println("bye");
        socket1.close();
        out2.println("bye");
        socket2.close();
    }
    
}
