package com.cpw.myclass.http

class RequestUrl {
    companion object {
        val pre_url = "http://106.13.235.246:1234/user/"
        val login = pre_url + "login/"
        val getUserMessage = pre_url + "get_user/"
        val getQuestion = pre_url + "getquestion/"
        val setQuestion = pre_url + "setquestion/"
        val addStudent = pre_url + "add_student/"
        val noticeToday = pre_url + "noticelist_today/"
        val readNotice = pre_url + "readnotice/"
        val userList = pre_url + "userlist/"
        val messageToMe = pre_url + "meslist_tome/"
        val messageNotRead = pre_url + "meslist_notread/"
        val messageOut = pre_url + "get_mes_out/"
        val messageList = pre_url + "messagelist/"
        val sendMessage = pre_url + "message/"
        val sendNotice = pre_url + "notice/"
        val readMessage = pre_url + "readmessage/"
    }
}