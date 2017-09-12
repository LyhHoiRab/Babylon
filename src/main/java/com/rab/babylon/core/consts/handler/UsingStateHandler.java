package com.rab.babylon.core.consts.handler;

import com.rab.babylon.core.consts.entity.UsingState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsingStateHandler extends BaseTypeHandler<UsingState> implements TypeHandler<UsingState>{

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UsingState parameter, JdbcType jdbcType) throws SQLException{
        ps.setInt(i, parameter.getId());
    }

    @Override
    public UsingState getNullableResult(ResultSet rs, String columnName) throws SQLException{
        return UsingState.getById(rs.getInt(columnName));
    }

    @Override
    public UsingState getNullableResult(ResultSet rs, int columnIndex) throws SQLException{
        return UsingState.getById(rs.getInt(columnIndex));
    }

    @Override
    public UsingState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        return UsingState.getById(cs.getInt(columnIndex));
    }
}
