package com.poll.persistence.mapper;

import com.poll.persistence.dto.AnswerDTO;
import com.poll.persistence.dto.QuestionDTO;
import com.poll.persistence.dto.SurveyCreateDTO;
import com.poll.persistence.dto.SurveyDTO;
import com.poll.persistence.model.*;
import com.poll.util.TimeUtil;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//@Mapper(uses = QuestionMapper.class)
//public interface SurveyMapper {
//    SurveyMapper MAPPER = Mappers.getMapper( SurveyMapper.class );
//
//    @Mappings({
//            @Mapping(source = "authorEmail", target = "surveyor.email"),
//            @Mapping(source = "created", dateFormat = "yyyy.MM.dd HH:mm:ss", target = "created"),
//            @Mapping(source = "updated", dateFormat = "yyyy.MM.dd HH:mm:ss", target = "updated"),
//            @Mapping(source = "expire", dateFormat = "yyyy.MM.dd HH:mm:ss", target = "expire"),
//    })
//    Survey toSurvey(SurveyDTO dto );
//
//    @InheritInverseConfiguration
//    SurveyDTO toSurveyDTO(Survey model );
//
//}

public class SurveyMapper {

    public static List<SurveyDTO> toSurveyDTOList(List<Survey> surveys) {
        List<SurveyDTO> dtoList = new ArrayList<>();
        for (Survey survey: surveys){
            dtoList.add(toSurveyDTO(survey));
        }
        return dtoList;
    }

    public static SurveyDTO toSurveyDTO(Survey survey) {
        SurveyDTO dto = new SurveyDTO();
        dto.setId(String.valueOf(survey.getId()));
        dto.setSurveyorEmail(survey.getSurveyorEmail());
        dto.setInvitedEmailList(survey.getInvitedEmailList());
        dto.setTitle(survey.getTitle());
        dto.setType(survey.getType().name());
        dto.setQuestions(QuestionMapper.toQuestions(survey.getQuestions()));
        dto.setAnswers(AnswerMapper.toAnswers(survey.getAnswers()));
        dto.setPublish(survey.getPublish());
        dto.setDeleted(survey.isDeleted());

        if (dto.getCreated() == null){
            dto.setCreated(TimeUtil.getDateString(new Date()));
        } else{
            dto.setCreated(TimeUtil.getDateString(survey.getCreated()));
        }

        if (dto.getUpdated() == null){
            dto.setUpdated(TimeUtil.getDateString(new Date()));
        } else{
            dto.setUpdated(TimeUtil.getDateString(survey.getUpdated()));
        }

        return dto;
    }


    public static Survey toSurvey(String surveyorEmail, SurveyCreateDTO surveyDTO) {
        Survey survey = new Survey(surveyorEmail, surveyDTO.getTitle(), surveyDTO.getType());
        return survey;
    }




    public static void updateSurvey(SurveyDTO surveyDTO, Survey survey) {
        survey.setSurveyorEmail(surveyDTO.getSurveyorEmail());
        survey.setInvitedEmailList(surveyDTO.getInvitedEmailList());
        survey.setTitle(surveyDTO.getTitle());
        survey.setType(SurveyType.getType(surveyDTO.getType()));

        survey.getQuestions().clear();
        for (QuestionDTO questionDTO: surveyDTO.getQuestions()) {
            Question question = QuestionMapper.toQuestion(questionDTO);
            question.setSurvey(survey);

            survey.getQuestions().add(question);
        }

        survey.getAnswers().clear();
        for (AnswerDTO answerDTO: surveyDTO.getAnswers()){
            Answer answer = AnswerMapper.toAnswer(answerDTO);
            answer.setSurvey(survey);
            survey.getAnswers().add(answer);
        }

        survey.setPublish(surveyDTO.getPublish());
        survey.setDeleted(surveyDTO.isDeleted());
        survey.setCreated(TimeUtil.getDate(surveyDTO.getCreated()));
        survey.setUpdated(TimeUtil.getDate(surveyDTO.getUpdated()));
    }


}