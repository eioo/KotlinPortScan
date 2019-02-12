package com.eioo.portscan

import java.io.IOException
import java.net.Socket
import java.util.concurrent.BlockingQueue
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingDeque

class Scanner(var queue: BlockingQueue<Int>, var host: String) : Runnable {
    override fun run() {
        try {
            while (!queue.isEmpty()) {
                val port = queue.take()
                val isOpen = checkPort(host, port)
                println("Port $port is: ${if (isOpen) "open" else "closed"}")
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }
}

fun doPortScan(host: String, ports: ArrayList<Int>, threadCount: Int) {
    val blockingQueue = LinkedBlockingDeque<Int>()
    val executor = Executors.newFixedThreadPool(threadCount)

    for (port in ports) {
        blockingQueue.put(port)
    }

    for (i in 1..threadCount) {
        executor.execute(Scanner(blockingQueue, host))
    }

    executor.shutdown()
    while (!executor.isTerminated) {
    }
}

fun checkPort(host: String, port: Int): Boolean {
    var s: Socket? = null

    return try {
        s = Socket(host, port)
        true
    } catch (e: IOException) {
        false
    } finally {
        if (s != null) {
            try {
                s.close();
            } catch (e: IOException) {
                throw RuntimeException("Failed to close socket gracefully.")
            }
        }
    }
}
