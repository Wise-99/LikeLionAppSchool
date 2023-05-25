import java.util.Scanner

fun main() {
    val tic = TicTacToe()
    tic.gameStart()
}

class TicTacToe {
    val scan = Scanner(System.`in`)

    // 보드를 저장할 array 3X3
    val board = arrayOf(
        arrayOf(" ", " ", " "),
        arrayOf(" ", " ", " "),
        arrayOf(" ", " ", " ")
    )

    // 입력 반복
    fun gameStart() {
        var num = 1
        while (true) {

            println("${num}번째 턴")
            printBoard()
            println()

            val turn = num % 2
            if (turn == 0) {
                print("Player2 입력(줄, 칸) : ")
            } else {
                print("Player1 입력(줄, 칸) : ")
            }

            val inputP1 = scan.next()

            try {
                val inputOX = inputP1.split(",")
                inputBoard(inputOX[0].toInt(), inputOX[1].toInt(), turn)
            } catch (e: Exception) {
                println("숫자,숫자로만 입력해주세요")
                continue
            }
            num++

            // 5턴 이상부터 빙고 확인
            if (num >= 5) {
                val check = printVictory(checkVictory(), num) // 승리자 또는 무승부 확인

                if (check == ""){       // 우승자가 없고 무승부도 아니라면 게임 진행
                    continue
                } else{                 // 결과가 나왔다면
                    printBoard()        // 완성된 보드 출력
                    println(check)      // 우승자 출력
                    break               // 게임 끝
                }
            }
        }
    }

    // 보드판 출력
    fun printBoard() {
        println("  0 1 2")

        // indices - 유효한 인덱스의 범위
        for (i in board.indices) {
            print("$i ")

            for (j in 0 until board[0].size) {
                print(board[i][j])
                if (j != 2)
                    print("|")
            }
            println()
            if (i != 2) {
                println("  -+-+-")
            }
        }
    }

    // 보드판 입력
    fun inputBoard(num1: Int, num2: Int, turn: Int) {
        // player2 차례
        if (turn == 0) {
            board[num1][num2] = "X"
        }

        // player1 차례
        else {
            board[num1][num2] = "O"
        }
    }

    // 빙고 완성 여부 확인
    fun checkVictory(): String {
        var checkChar = ""

        // indices - 유효한 인덱스의 범위
        for (i in board.indices){
            // 세로 확인
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] == board[2][i]){
                // O, X 리턴
                checkChar = board[0][i]
                break
            }

            // 가로 확인
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] == board[i][2]){
                checkChar = board[i][0]
                break
            }
        }

        // 대각선 확인
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] == board[2][2]) {
            checkChar = board[0][0]
        } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] == board[2][0]) {
            checkChar = board[0][2]
        }

        // 완성된 줄이 없으면 "" 리턴
        return checkChar
    }

    // 승리자 출력
    fun printVictory(check:String, num:Int): String {
        if (check == "O") {
            return "player1 승리!"
        } else if (check == "X") {
            return "player2 승리!"
        }

        // 9칸을 다 채우고 checkVictory()에서 넘겨준 값이 "" 라면 무승부
        if (num == 9 && check == ""){
            return "무승부!"
        }

        return ""
    }
}