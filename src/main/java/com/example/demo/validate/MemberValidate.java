package com.example.demo.validate;

import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.exception.ExceptionGenerator;
import com.example.demo.exception.StatusEnum;
import com.example.demo.service.MemberService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberValidate {
    private final MemberService memberService;

    MemberValidate(MemberService memberService) {
        this.memberService = memberService;
    }

    public void validateCreateContainId(Long id) {
        if (memberService.getAll().stream()
                .noneMatch(member -> member.id().equals(id)))
            throw new ExceptionGenerator(StatusEnum.CREATE_NOT_PRESENT_BOARD);
    }

    public void validateResponseIsEmpty(List<MemberResponse> response) {
        if (response.isEmpty())
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER);
    }

    public void validateReadContainId(Long id) {
        if (memberService.getAll().stream()
                .noneMatch(member -> member.id().equals(id)))
            throw new ExceptionGenerator(StatusEnum.READ_NOT_PRESENT_MEMBER);
    }

    public void validateExistEmail(String email) {
        if (memberService.getAll().stream()
                .anyMatch(member -> member.email().equals(email)))
            throw new ExceptionGenerator(StatusEnum.CREATE_OR_EDIT_CONFLICT_EMAIL);
    }
}
