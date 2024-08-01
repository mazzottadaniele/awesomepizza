package it.adesso.awesomepizza.data.transformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

public abstract class GenericTransformer<E, D> {
	@SuppressWarnings("unchecked")
	public E toEntity(D dto) {
		E entity = null;
		if(dto!=null) {
			try {
				entity = ((Class<E>)(((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0])).getDeclaredConstructor().newInstance();
				BeanUtils.copyProperties(dto, entity);
			} catch (RuntimeException e) {
				throw e;
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	public D toDTO(E entity) {
		D dto = null;
		if(entity!=null) {
			try {
				dto = ((Class<D>)(((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[1])).getDeclaredConstructor().newInstance();
				BeanUtils.copyProperties(entity, dto);
			} catch (RuntimeException e) {
				throw e;
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		}
		return dto;
	}
	
	public List<E> toEntitys(Collection<D> dtos) throws RuntimeException {
		List<E> eList = null;
		if(dtos!=null) {
			eList = dtos.stream().map((dto)-> {
				try {
					return this.toEntity(dto);
				} catch (RuntimeException e) {
					throw e;
				} catch(Exception e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
		}
		return eList;
	}
	
	public List<D> toDTOs(Collection<E> entities) throws RuntimeException {
		List<D> dList = null;
		if(entities!=null) {
			dList = entities.stream().map((entity)-> {
				try {
					return this.toDTO(entity);
				} catch (RuntimeException e) {
					throw e;
				} catch(Exception e) {
					throw new RuntimeException(e);
				}
			}).collect(Collectors.toList());
		}
		return dList;
	}
}
