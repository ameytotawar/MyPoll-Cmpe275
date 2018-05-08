package com.poll.service;

import com.poll.persistence.dto.QuestionDTO;
import com.poll.persistence.dto.SurveySaveDTO;
import com.poll.persistence.mapper.QuestionMapper;
import com.poll.persistence.mapper.SurveyMapper;
import com.poll.persistence.dto.SurveyCreateDTO;
import com.poll.persistence.dto.SurveyDTO;
import com.poll.persistence.model.AppUser;
import com.poll.persistence.model.Question;
import com.poll.persistence.model.Survey;
import com.poll.persistence.model.SurveyType;
import com.poll.persistence.repository.AppUserRepository;
import com.poll.persistence.repository.SurveyRepository;
import com.poll.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SurveyService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private SurveyRepository surveyRepository;


    private void saveSurvey(Survey survey) {
        surveyRepository.save(survey);
    }

    public Survey createSurvey(AppUser surveyor, SurveyType type) {
        if (surveyor == null || type == null){
            return null;
        }
        Survey survey = new Survey(surveyor, type);
        saveSurvey(survey);
        return survey;
    }

    public SurveyDTO createSurvey(String surveyorEmail, SurveyCreateDTO surveyDTO) {
        AppUser user = appUserRepository.findByEmail(surveyorEmail);
        Survey survey = createSurvey(user, SurveyType.getType(surveyDTO.getType()));

        return SurveyMapper.MAPPER.toSurveyDTO(survey);
    }

    public List<SurveyDTO> findAllBySurveyorId(Long id) {
        List<Survey> surveys = surveyRepository.findAllBySurveyorId(id);
        List<SurveyDTO> dtoList = new ArrayList<>();
        for (Survey survey: surveys){
            dtoList.add(SurveyMapper.MAPPER.toSurveyDTO(survey));
        }
        return dtoList;
    }

    public SurveyDTO findById(long id) {
        Survey survey = surveyRepository.findById(id);
        return SurveyMapper.MAPPER.toSurveyDTO(survey);
    }

    public boolean authorized(String surveyorEmail, long id) {
        Survey survey = surveyRepository.findById(id);
        return survey.getSurveyor().getEmail().equals(surveyorEmail);
    }

    public Survey save(long surveyId, SurveySaveDTO surveyDTO) {
        System.out.println("==> SurveyService.save");
//        System.out.println("surveyId = " + surveyId);
//        System.out.println("surveyDTO.getQuestions() = " + surveyDTO.getQuestions());
        Survey survey = surveyRepository.findById(surveyId);
        survey.setTitle(surveyDTO.getTitle());
        survey.setInvitedEmailList(surveyDTO.getInvitedEmailList());


        List<Question> questions = new ArrayList<>();
        List<QuestionDTO> dtoList = surveyDTO.getQuestions();
        for (QuestionDTO dto: dtoList){
            Question question = QuestionMapper.MAPPER.toQuestion(dto);
            questions.add(question);
        }
        survey.setQuestions(questions);

//        survey.setQuestions(QuestionMapper.MAPPER.map(surveyDTO.getQuestions()));
        surveyRepository.save(survey);
        return survey;
    }
}
