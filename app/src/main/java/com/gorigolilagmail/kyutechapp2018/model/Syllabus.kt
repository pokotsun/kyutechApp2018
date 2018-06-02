package com.gorigolilagmail.kyutechapp2018.model


data class Syllabus(
        val id: Int,
        val title: String,
        val subjectCode: Int,
        val teacherName: String,
        val targetParticipantsInfos: List<TargetParticipantsInfo>,
        val targetSchoolYear: String,
        val targetTerm: String,
        val classNumber: Int,
        val targetPeriod: String,
        val targetPlace: String,
        val publishedDate: String,
        val abstract: String,
        val positioning: String,
        val lectureContent: String,
        val lectureProcessing: String,
        val performanceTarget: String,
        val valuationBasis: String,
        val instructionOutLearning: String,
        val keywords: String,
        val textBooks: String,
        val studyAidBooks: String,
        val notes: String,
        val professorEmail: String

) {
    companion object {
        fun createDummy(): Syllabus = Syllabus(
                id = 1111, title = "ダミータイトル", subjectCode = 3, teacherName = "山田太郎",
                targetParticipantsInfos = listOf(TargetParticipantsInfo.createDummy(), TargetParticipantsInfo.createDummy()),
                targetSchoolYear = "4年", targetTerm = "通年", classNumber=1, targetPeriod = "", targetPlace = "1103講義室",
                publishedDate="2018/03/08 00:00", abstract = "概要", positioning = "授業の立ち位置", lectureContent = "授業内容",
                lectureProcessing = "授業の進め方", performanceTarget = "授業ターゲット", valuationBasis = "評価基準", instructionOutLearning = "授業外学習のすすめ",
                keywords = "キーワード", textBooks = "教科書", studyAidBooks = "補助本", notes="ノート", professorEmail = "aiueo@gmail.com"
        )
    }
}


data class TargetParticipantsInfo(
        val targetParticipants: String,
        val academicCreditKind: String,
        val academicCreditNum: Double
) {
    companion object {
        fun createDummy(): TargetParticipantsInfo = TargetParticipantsInfo(
                targetParticipants = "情報工学部　生命情報工学科　生命情報工学科",
                academicCreditKind = "必",
                academicCreditNum = 8.0
        )
    }
}

