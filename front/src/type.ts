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

export interface Exercise {
    exerciseId: number
    exerciseName: string
    partId: number
    difficulty: number
    calories: number
    description: string
    precautions: string
  }
  
  export interface BodyPart {
    partId: number
    partName: string
  }