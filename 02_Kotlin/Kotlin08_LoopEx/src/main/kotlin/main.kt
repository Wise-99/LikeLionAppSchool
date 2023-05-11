import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    print("학생 수 입력 : ")
    val num = scanner.nextInt()
   /* 학생수를 입력 받는다.
    입력받은 학생의 수 만큼 반복하며
    학생의 이름, 학년, 국어, 영어, 수학 점수를 받는다.
    입력이 완료되면 각 학생들의 정보를 출력하고 각 과목별 총점과 평균을 출력한다.
    학생의 정보는 각각을 ArrayList를 만들어서 각각 담아준다.*/

    val nameList = ArrayList<String>()
    val gradeList = ArrayList<Int>()
    val koreanList = ArrayList<Int>()
    val engList = ArrayList<Int>()
    val mathList = ArrayList<Int>()


    for (i in 0 until num){
        print("이름 입력 : ")
        nameList.add(scanner.next())

        print("학년 입력 : ")
        gradeList.add(scanner.nextInt())

        print("국어 점수 입력 : ")
        koreanList.add(scanner.nextInt())

        print("영어 점수 입력 : ")
        engList.add(scanner.nextInt())

        print("수학 점수 입력 : ")
        mathList.add(scanner.nextInt())

        println("---------------------------")
    }

    var koreanTotal = 0.0
    var engTotal = 0.0
    var mathTotal = 0.0

    for(i in 0 until num){
        println("""이름 : ${nameList[i]}
            |학년 : ${gradeList[i]}
            |국어 점수 : ${koreanList[i]}
            |영어 점수 : ${engList[i]}
            |수학 점수 : ${mathList[i]}
        """.trimMargin())
        println("---------------------------")
        koreanTotal += koreanList[i]
        engTotal += engList[i]
        mathTotal += mathList[i]
    }

    println("----- 과목별 총점 -----")
    println("""국어 총점 : $koreanTotal
        |영어 총점 : $engTotal
        |수학 총점 : $mathTotal
    """.trimMargin())

    println("----- 과목별 평균 -----")
    println("""국어 평균 : ${koreanTotal/num}
        |영어 평균 : ${engTotal/num}
        |수학 평균 : ${mathTotal/num}
    """.trimMargin())

}