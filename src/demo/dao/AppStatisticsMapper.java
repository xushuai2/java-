package demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import demo.Entity.AppStatistics;
import demo.exception.DAOException;

public interface AppStatisticsMapper {
    int deleteByPrimaryKey(Integer id);

    public int  updateByDuoXC(AppStatistics a) throws DAOException;
    
    int insert(AppStatistics record) ;

    int insertSelective(AppStatistics record);

    AppStatistics selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AppStatistics record);

    int updateByPrimaryKey(AppStatistics record);
    
    List<AppStatistics> getBarChart(@Param("type") String type);
}