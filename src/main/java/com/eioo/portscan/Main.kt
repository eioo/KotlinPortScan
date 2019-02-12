package com.eioo.portscan

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import kotlin.system.exitProcess


fun main(args: Array<String>): Unit = mainBody {
    val parsedArgs = ArgParser(args).parseInto(::ProgArgs)

    val portSingleRegex = """^\d{1,5}$""".toRegex()
    val portDelimRegex = """^(,?\d{1,5})+${'$'}""".toRegex()
    val portRangeRegex = """^\d{1,5}-\d{1,5}$""".toRegex()

    parsedArgs.run {
        println(
            """
            Host: $host
            Port: $port
            Threads: $threads
            ---------------
        """.trimIndent()
        )

        var ports: ArrayList<Int> = ArrayList()

        when {
            portSingleRegex.containsMatchIn(port) -> ports = extractSingleInt(port)
            portDelimRegex.containsMatchIn(port) -> ports = extractSeperatedInts(port, ",")
            portRangeRegex.containsMatchIn(port) -> ports = extractRangeInts(port)
        }

        if (ports.size == 0) {
            println("No valid ports")
            exitProcess(0)
        }

        doPortScan(host, ports, threads)
        println("---------------\nScan done")
    }
}

class ProgArgs(parser: ArgParser) {
    val host by parser.storing(
        "-H", "--host",
        help = "host to scan ports on"
    )

    val port by parser.storing(
        "-p", "--port",
        help = "ports to scan (Examples: 80 | 80,81,82 | 80-100)"
    )

    val threads by parser.storing(
        "-t", "--threads",
        help = "max thread count"
    ) { toInt() }
}