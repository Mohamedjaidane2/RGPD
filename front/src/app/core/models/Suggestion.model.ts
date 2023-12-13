import { gradingScaleResponse } from './GradingScale.model';

export interface SuggestionModel {
  id: string;
  title: string;
  creationDate: Date;
  refS: string;
  gradingScale: gradingScaleResponse;
}

export interface GetSuggestionByTopicModel {
  id: string;
  reference: string;
}

export interface UpdateSuggestionDto {
  gradingScaleId: string;
  id: string;
  refS: string;
  title: string;
}
