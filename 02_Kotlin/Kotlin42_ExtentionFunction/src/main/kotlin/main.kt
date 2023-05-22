import java.util.*

fun main() {
    // 확장함수
    //
    // 자바 코드로 변경될 때 객체의 ID를 받아 사용하는 코드로 변경된다.

    val str1 = "abcd"

    // 추가한 메서드 호출
    println(str1.getUpperString())
    println(str1.printString())
}

// 확장함수 정의
// 클래스명.추가할 메서드
fun String.getUpperString() : String {
    return this.uppercase(Locale.getDefault())
}

fun String.printString() {
    println("문자열 :  $this")
}