import { Activity_area_enum } from '../enum/ActivityArea.enum';
import { Workforce_enum } from '../enum/Workforce.enum';

export interface QuestionSuggestionSrcWithJoin {
  questionId: string;
  suggestionsSrc: SuggestionSrc[];
}

export interface SuggestionSrc {
  creationDate: string;
  id: string;
  refS: string;
  title: string;
}

export interface ResponseTest {
  created: string;
  guest: {
    created: string;
    email: string;
    expertiseField: string;
    firstName: string;
    function: string;
    id: string;
    lastName: string;
    organisation: {
      activityArea: Activity_area_enum;
      created: string;
      expertiseField: string;
      name: string;
      workforce: Workforce_enum;
    };
    profile: string;
    telephone: string;
  };
  id: string;
  penaltyAmount: number;
  penaltyJail: number;
  refT: string;
  score: number;
  totalCorrectAnswers: number;
}

export interface AnswersDto {
  test: ResponseTest;
  topic: ResponseTopics[];
}

export interface ResponseTopics {
  questions: Questions[];
  topic: Topic;
}

export interface Questions {
  answers: Answer[];
  question: Question;
}

export interface Answer {
  created: string;
  gradingScaleId: string;
  id: string;
  refS: string;
  title: string;
}

export class Question {
  created!: string;
  id!: string;
  refQ!: string;
  subQuestions!: Question[];
  suggestionSrc!: SuggestionSrc;
  suggestions!: Suggestion[];
  title!: string;
  topic!: Topic;
  type!: string;
}

export class Topic {
  created!: string;
  id!: string;
  title!: string;
}

export class Suggestion {
  created!: string;
  gradingScaleId!: string;
  id!: string;
  refS!: string;
  title!: string;
}
