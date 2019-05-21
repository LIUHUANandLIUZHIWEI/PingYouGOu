package cn.pinyougou.mapper;

import cn.pinyougou.pojo.TbItem;
import cn.pinyougou.pojo.TbItemExample;

import java.io.Serializable;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbItemMapper extends Serializable{
    int countByExample(TbItemExample example);

    int deleteByExample(TbItemExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbItem record);

    int insertSelective(TbItem record);

    List<TbItem> selectByExample(TbItemExample example);

    TbItem selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByExample(@Param("record") TbItem record, @Param("example") TbItemExample example);

    int updateByPrimaryKeySelective(TbItem record);

    int updateByPrimaryKey(TbItem record);
}