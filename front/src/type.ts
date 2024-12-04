export interface UserInfo {
    username: string;
    email: string;
    name: string;
    phoneNumber: string;
    birthDate: string;
    avatar: string | null;
    status: string;
    createTime: string;
}

export interface ApiResponse<T> {
    msg: string;
    code: number;
    data: T;
}

export interface Question {
    questionId: number
    questionText: string
    options: Array<{
        optionId: number
        optionName: string
        optionText: string
    }>
}

export interface TestResult {
    totalScore: number
    aiAnalysis: string
}

export interface BodyPart {
    partId: number;
    partName: string;
}

export interface Exercise {
    exerciseId: number;
    exerciseName: string;
    description: string;
    bodyParts: BodyPart[];
}

export interface TableDataInfo<T> {
  total: number;
  rows: T[];
  code: number;
  msg: string | null;
}