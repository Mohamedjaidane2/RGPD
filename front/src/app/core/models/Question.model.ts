import { Question_type_enum } from '../enum/QuestionType.enum';
import { SuggestionModel } from './Suggestion.model';
import { Topic } from './TopicModel';

export interface QuestionModel {
  refQ: string;
  id: string;
  topic: Topic;
  title: string;
  type: Question_type_enum;
  created: Date;
  suggestions: SuggestionModel[];
  suggestionSrc: SuggestionModel[];
}

export interface GetAllQuestionsDto {
  questions: QuestionModel[];
  topic: Topic;
}

export interface UpdateQuestionDto {
  id: string;
  refQ: string;
  subQuestionsIds: string[];
  suggestionSrcId: string;
  suggestionsIds: string[];
  title: string;
  topicId: string;
  type: Question_type_enum;
}
