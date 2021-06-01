package pers.gon.infrastructure.common.controller;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import pers.gon.infrastructure.common.entity.BaseEntity;
import pers.gon.infrastructure.common.entity.CommonResult;
import pers.gon.infrastructure.common.valid.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	protected Validator validator;

	protected  <T extends BaseEntity> String saveValidate(T vo){

		String saveGroupMsg = this.validate(vo, SaveGroup.class);
		if(StrUtil.isNotEmpty(saveGroupMsg)){
			return saveGroupMsg;
		}

	  if(vo.isNewRecord()){
			String insertMsg = this.validate(vo, InsertGroup.class);
			if(StrUtil.isNotEmpty(insertMsg)){
				return insertMsg;
			}
		}else{
			String updateMsg = this.validate(vo, UpdateGroup.class);
			if(StrUtil.isNotEmpty(updateMsg)){
				return updateMsg;
			}
		}
		return null;
	}

	protected  <T extends BaseEntity> String queryValidate(T vo){

		String saveGroupMsg = this.validate(vo, QueryGroup.class);
		if(StrUtil.isNotEmpty(saveGroupMsg)){
			return saveGroupMsg;
		}
		return null;
	}

	protected  <T extends BaseEntity> String deleteValidate(T vo){

		String deleteMsg = this.validate(vo, DeleteGroup.class);
		if(StrUtil.isNotEmpty(deleteMsg)){
			return deleteMsg;
		}
		return null;
	}


	protected  <T> String validate(T vo, Class<?>... group){

		Set<ConstraintViolation<T>> errorSet =  validator.validate(vo, group);
		if(!errorSet.isEmpty()){
			return errorSet.stream().map(item-> item.getMessage()).collect(Collectors.joining(","));
		}
		return null;
	}

	@SneakyThrows
	protected OutputStream getExcelStream(String filename){
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		String fileName = URLEncoder.encode(filename, "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		return response.getOutputStream();
	}

}
