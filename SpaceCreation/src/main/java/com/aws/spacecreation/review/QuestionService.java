package com.aws.spacecreation.review;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aws.spacecreation.DataNotFoundException;

@Service
@Transactional
public class QuestionService {
	@Autowired
	private EmailService emailservice;
	
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private JavaMailSender mailSender;
    
    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Transactional
    public Question getQuestion(Integer id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();
            increaseViews(question); // 조회수 증가 메서드 호출
            return question;
        } else {
            throw new DataNotFoundException("Question not found with id: " + id);
        }
    }

    @Transactional
    public void create(Question question) {
    	emailservice.sendEmailFromDaum(question);
    	question.setCreateDate(LocalDateTime.now());
        questionRepository.save(question);
        
    }

    @Transactional
    public void delete(Integer id) {
        questionRepository.deleteById(id);
    }
    
    // 조회수 증가 메서드
    private void increaseViews(Question question) {
        int views = question.getViews();
        question.setViews(views + 1);
        questionRepository.save(question); // 변경된 조회수를 저장
    }
    
    @Transactional
    public void deleteQuestion(Integer id) {
    	questionRepository.deleteById(id);
    }
}
