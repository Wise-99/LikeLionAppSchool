import java.util.Scanner

class MainMenuClass(var scanner : Scanner) {

    fun inputMainMenuNumber() : Int{
        while (true){
            try {
                println()
                println("1. 메모 카테고리 관리")
                println("2. 메모 카테고리 선택")
                println("3. 메모 내용 전체 보기")
                println("4. 종료")
                print("메뉴를 선택해주세요 : ")

                val inputNumber = scanner.next().toInt()

                if (inputNumber !in 1 .. 4){
                    println()
                    println("잘못 입력 하였습니다.")
                } else {
                    return inputNumber
                }
            } catch (e:Exception){
                println("잘못 입력하였습니다.")
            }
        }
    }
}

enum class MainMenuItem(val itemNumber:Int){
    // 메인 메뉴
    MAIN_MENU_MANAGEMENT_CARTEGORY(1),

    // 카테고리 선택
    MAIN_MENU_CHOICE_CARTEGORY(2),

    // 메모 내용 전체 보기
    MAIN_MENU_SHOW_MEMO_ALL(3),

    // 프로그램 종료
    MAIN_MENU_ITEM_EXIT(4)
}