export class TestAnswers {
  questionId!: string;
  suggestions!: string[];
}

export class AnswerModel {
  answers!: TestAnswers[];
  testId!: string;
}
