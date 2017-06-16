package hbc315.HIDC.dao;

import hbc315.HIDC.model.CallDetail;

public interface CallDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CallDetail record);

    int insertSelective(CallDetail record);

    CallDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CallDetail record);

    int updateByPrimaryKey(CallDetail record);
}