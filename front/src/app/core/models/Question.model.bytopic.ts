import { Suggestion, SuggestionSrc } from './Answers.dto';

export class QuestionByTopic {
  id!: string;
  title!: string;
  type!: string;
  refQ!: string;
  intRefQ!: number;
  topic!: {
    id: string;
    title: string;
    created: string;
  };
  suggestionSrc!: SuggestionSrc[];
  subQuestions!: any[];
  suggestions!: {
    intRefS: number;
    id: string;
    title: string;
    refS: string;
    created: string;
  }[];
  display!: boolean;
  created!: string;
}
