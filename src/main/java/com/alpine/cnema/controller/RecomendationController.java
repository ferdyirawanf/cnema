package com.alpine.cnema.controller;

import com.alpine.cnema.dto.response.RenderJson;
import com.alpine.cnema.model.User;
import com.alpine.cnema.service.RecomendationService;
import com.alpine.cnema.utils.constant.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recomendations")
@RequiredArgsConstructor
public class RecomendationController {
    private final RecomendationService recomendationService;

    @GetMapping()
    public ResponseEntity<?> getRecomendation(@AuthenticationPrincipal User user) {
        return RenderJson.basicFormat(recomendationService.getAll(user), HttpStatus.OK, Messages.RECOMENDATION_FOUND);
    }
}
