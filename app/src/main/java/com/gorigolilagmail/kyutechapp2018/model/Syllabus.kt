package com.gorigolilagmail.kyutechapp2018.model

import android.os.Parcel
import android.os.Parcelable

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

): Parcelable {

    fun convertTargetParticipants2showingText(): String {
        var result = ""
        targetParticipantsInfos.forEach { targetParticipantsInfo ->
            result += targetParticipantsInfo.toShowingText() + "\n\n"
        }
        return result
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = dest.run {
        writeInt(id)
        writeString(title)
        writeInt(subjectCode)
        writeString(teacherName)
        writeTypedList(targetParticipantsInfos)
        writeString(targetSchoolYear)
        writeString(targetTerm)
        writeInt(classNumber)
        writeString(targetPeriod)
        writeString(targetPlace)
        writeString(publishedDate)
        writeString(abstract)
        writeString(positioning)
        writeString(lectureContent)
        writeString(lectureProcessing)
        writeString(performanceTarget)
        writeString(valuationBasis)
        writeString(instructionOutLearning)
        writeString(keywords)
        writeString(textBooks)
        writeString(studyAidBooks)
        writeString(notes)
        writeString(professorEmail)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Syllabus> = object: Parcelable.Creator<Syllabus> {
            override fun createFromParcel(source: Parcel): Syllabus = source.run {
                Syllabus(readInt(), readString(), readInt(), readString(),
                        mutableListOf<TargetParticipantsInfo>().apply { readTypedList(this, TargetParticipantsInfo.CREATOR)},
                        readString(), readString(), readInt(),
                        readString(), readString(), readString(),readString(),
                        readString(), readString(),readString(),readString(),
                        readString(), readString(),readString(),readString(),
                        readString(), readString(),readString()
                )
            }

            override fun newArray(size: Int): Array<Syllabus?> = arrayOfNulls(size)
        }

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
): Parcelable {
    fun toShowingText(): String =
            "${targetParticipants} $academicCreditKind $academicCreditNum"

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = dest.run {
        writeString(targetParticipants)
        writeString(academicCreditKind)
        writeDouble(academicCreditNum)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<TargetParticipantsInfo> = object: Parcelable.Creator<TargetParticipantsInfo> {
            override fun createFromParcel(source: Parcel): TargetParticipantsInfo = source.run {
                TargetParticipantsInfo(readString(), readString(), readDouble())
            }

            override fun newArray(size: Int): Array<TargetParticipantsInfo?> = arrayOfNulls(size)
        }

        fun createDummy(): TargetParticipantsInfo = TargetParticipantsInfo(
                targetParticipants = "情報工学部　生命情報工学科　生命情報工学科",
                academicCreditKind = "必",
                academicCreditNum = 8.0
        )
    }
}

