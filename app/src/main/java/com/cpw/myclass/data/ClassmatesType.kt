package com.cpw.myclass.data

enum class ClassmatesType(i: Int){
    ClassTeacher(0),
    Schoolmate(6),
    Monitor(1),
    ViceMonitor(2),
    StudyMonitor(3),
    SportsMonitor(4),
    LeagueBranchSecretary(5);

    companion object {
        fun getClassmatesType(i: Int): ClassmatesType? {
            return when (i) {
                6 -> Schoolmate
                1 -> Monitor
                2 -> ViceMonitor
                3 -> StudyMonitor
                4 -> SportsMonitor
                5 -> LeagueBranchSecretary
                0 -> ClassTeacher
                else -> null
            }
        }

        fun getRoleString(i: Int): String {
            return when (i) {
                1 -> "班长"
                2 -> "副班长"
                3 -> "学习委员"
                4 -> "文体委员"
                5 -> "团支部书记"
                0 -> "班主任"
                else -> "同学"
            }
        }
    }
}