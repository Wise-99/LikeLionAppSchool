import java.util.*

/* 학생수를 입력 받는다.
    입력받은 학생의 수 만큼 반복하며
    학생의 이름, 학년, 국어, 영어, 수학 점수를 받는다.
    입력이 완료되면 각 학생들의 정보를 출력하고 각 과목별 총점과 평균을 출력한다.
    학생의 정보는 각각을 ArrayList를 만들어서 각각 담아준다.*/

// 점수를 받을 ArrayList
val nameList = ArrayList<String>()
val gradeList = ArrayList<Int>()
val koreanList = ArrayList<Int>()
val englishList = ArrayList<Int>()
val mathList = ArrayList<Int>()

val Scanner = Scanner(System.`in`)

fun main() {
    /* 내 코드
    val scanner = Scanner(System.`in`)
    print("학생 수 입력 : ")
    val num = scanner.nextInt()

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
    """.trimMargin())*/

    // 학생 수를 입력받는다.
    val studentCount = inputStudentCount()
    // 학생 정보를 입력받는다.
    inputStudentInfo(studentCount)
    // 학생 정보를 출력한다.
    printStudentInfo(studentCount)
    // 과목별 총점과 평균을 출력한다.
    printTotalAvg(studentCount)
}

// 학생 수를 입력받는 함수
fun inputStudentCount() : Int{
    print("학생 수를 입력해주세요 : ")
    val studentCount = Scanner.nextInt()
    return studentCount
}
// 학생의 정보를 입력받는 함수
fun inputStudentInfo(studentCount : Int){
    for (a1 in 1..studentCount){
        print("이름 : ")
        val name = Scanner.next()
        print("학년 : ")
        val grade = Scanner.nextInt()
        print("국어 : ")
        val korean = Scanner.nextInt()
        print("영어 : ")
        val english = Scanner.nextInt()
        print("수학 : ")
        val math = Scanner.nextInt()

        nameList.add(name)
        gradeList.add(grade)
        koreanList.add(korean)
        englishList.add(english)
        mathList.add(math)
    }
}
// 학생들의 정보를 출력하는 함수
fun printStudentInfo(studentCount : Int){
    for (a1 in 1..studentCount){
        println("이름 : ${nameList[a1-1]}")
        println("학년 : ${gradeList[a1-1]}")
        println("국어 : ${koreanList[a1-1]}")
        println("영어 : ${englishList[a1-1]}")
        println("수학 : ${mathList[a1-1]}")
    }
}
// 각 과목별 총점과 평균을 출력하는 함수
fun printTotalAvg(studentCount : Int){
    // 과목별 총점을 담을 변수
    var koreanTotal = 0
    var englishTotal = 0
    var mathTotal = 0

    for (a1 in 1..studentCount){
        koreanTotal += koreanList[a1 - 1]
        englishTotal += englishList[a1 - 1]
        mathTotal += mathList[a1 - 1]
    }

    // 과목별 평균을 구한다.
    val koreanAvg = koreanTotal / studentCount
    val englishAvg = englishTotal / studentCount
    val mathAvg = mathTotal / studentCount

    // 출력한다.
    println("국어 총점 : $koreanTotal")
    println("영어 총점 : $englishTotal")
    println("수학 총점 : $mathTotal")
    println("국어 평균 : $koreanAvg")
    println("영어 평균 : $englishAvg")
    println("수학 평균 : $mathAvg")
}