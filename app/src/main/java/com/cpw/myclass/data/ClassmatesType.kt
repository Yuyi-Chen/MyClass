package com.cpw.myclass.data

enum class ClassmatesType(i: Int){
    Schoolmate(0),
    Monitor(1),
    ViceMonitor(2),
    StudyMonitor(3),
    SportsMonitor(4),
    LeagueBranchSecretary(5),
    ClassTeacher(6);

    companion object {
        fun getClassmatesType(i: Int): ClassmatesType? {
            return when (i) {
                0 -> Schoolmate
                1 -> Monitor
                2 -> ViceMonitor
                3 -> StudyMonitor
                4 -> SportsMonitor
                5 -> LeagueBranchSecretary
                6 -> ClassTeacher
                else -> null
            }
        }
    }
}