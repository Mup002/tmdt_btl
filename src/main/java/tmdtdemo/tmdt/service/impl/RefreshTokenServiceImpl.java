package tmdtdemo.tmdt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tmdtdemo.tmdt.dto.response.RefreshTokenResponse;
import tmdtdemo.tmdt.entity.RefreshToken;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.exception.ResourceNotFoundException;
import tmdtdemo.tmdt.exception.TokenExpirationException;
import tmdtdemo.tmdt.repository.RefreshTokenRepository;
import tmdtdemo.tmdt.service.RefreshTokenService;
import tmdtdemo.tmdt.utils.DateFormat;
import tmdtdemo.tmdt.utils.Mapper;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository tokenRepository;
    @Override
    public boolean checkTokenNoExpireByUser(Long userId) {
        RefreshToken rf = tokenRepository.findRefreshTokenByUserId(userId);
        if(ObjectUtils.isEmpty(rf)){
            return false;
        }
        String now = new Date().toString();
        System.out.println(DateFormat.dateFormatWithLocate(now));
        System.out.println(rf.getRefreshExpiration().toString());
        if(DateFormat.compareDateTimeStrings(DateFormat.dateFormatWithLocate(now),rf.getRefreshExpiration().toString()) == 1){
            tokenRepository.delete(rf);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String disableToken(Long userId) {
        RefreshToken rf = tokenRepository.findRefreshTokenByUserId(userId);
        rf.setStatus(false);
        tokenRepository.save(rf);
        return "token has disable";
    }
    @Override
    public String undisableToken(Long userId) {
        RefreshToken rf = tokenRepository.findRefreshTokenByUserId(userId);
        rf.setStatus(true);
        tokenRepository.save(rf);
        return "token has available";
    }

    @Override
    public String removeToken(Long userId) {
        RefreshToken rf = tokenRepository.findRefreshTokenByUserId(userId);
        if(ObjectUtils.isEmpty(rf)){
            throw new ResourceNotFoundException("RefreshToken dont exists");
        }
        String now = new Date().toString();
        if(DateFormat.compareDateTimeStrings(now,rf.getRefreshExpiration().toString()) == 1){
            tokenRepository.delete(rf);
            return "deleted";
        }else{
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()),"Sth wrong");
        }
    }

    @Override
    public RefreshTokenResponse getRefreshTokenByUser(Long userId) {
        RefreshToken rf = tokenRepository.findRefreshTokenByUserId(userId);
        if(ObjectUtils.isEmpty(rf)){
            throw new ResourceNotFoundException("RefreshToken dont exists");
        }
        return Mapper.refreshTokenToResponse(rf);
    }

    @Override
    public String savedRefreshToken(RefreshToken refreshToken) {
        RefreshToken rf = new RefreshToken();
        rf.setRefreshToken(refreshToken.getRefreshToken());
        rf.setRefreshExpiration(refreshToken.getRefreshExpiration());
        rf.setStatus(refreshToken.isStatus());
        rf.setUser(refreshToken.getUser());
        tokenRepository.save(rf);
        return "created!";
    }

    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        RefreshToken rf = tokenRepository.findRefreshTokenByRefreshToken(refreshToken);
        if(ObjectUtils.isEmpty(rf)){
            throw new ResourceNotFoundException("RefreshToken dont exists");
        }
        if(rf.isStatus() == false){
            throw new TokenExpirationException("Refresh Token has expired");
        }
        return rf;
    }
}
