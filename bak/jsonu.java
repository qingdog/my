package com.landray.kmss.km.agreement.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.logging.Log;

import com.landray.kmss.sys.organization.model.SysOrgElement;

import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;

interface JsonUtilClassCustomizer<T> {
	Class<?> clazz = JsonUtilClassCustomizer.class;
	public abstract JSONObject getValue(T object);
	default void ClassCustomizer() {
//		clazz = Object.class;
		
//		Type type = this.getClass().getGenericSuperclass();
//		if (type instanceof ParameterizedType) {
//			ParameterizedType parameterizedType = (ParameterizedType) type;
//			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//			if (actualTypeArguments.length > 0) {
//				Type actualTypeArgument = actualTypeArguments[0];
//				if (actualTypeArgument instanceof Class) {
//					clazz = (Class<?>) actualTypeArgument;
//				}
//			}
//		}
	}
}

public class JsonUtil {
	
	public static void main(String[] args) {
		JSONObject jsonObject = new JsonUtil().beanToJson(String.valueOf(123));
		System.out.println(jsonObject);
	}

	private int recursionTimes = 4;
	private String dateFormat = "yyyy-MM-dd HH:mm";
	private final List<HashMap<Integer, Object>> recursionObject = new ArrayList<>();
//	private static final HashSet<String> EMPTY_EXCLUDNAME;
//	static {
//		EMPTY_EXCLUDNAME = new HashSet<String>();
//	}
	private final HashSet<String> excludeFieldNames = new HashSet<>();
	// �Ǿ�̬�ڲ��࣬�ȴ����ⲿ���ʵ��

	public abstract class ClassCustomizer<T> {
		Class<?> clazz = this.getClass();
		public abstract JSONObject getValue(T object);
		ClassCustomizer() {
			Type type = this.getClass().getGenericSuperclass();
			if (type instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				if (actualTypeArguments.length > 0) {
					Type actualTypeArgument = actualTypeArguments[0];
					if (actualTypeArgument instanceof Class) {
						this.clazz = (Class<?>) actualTypeArgument;
					}
				}
			}
		}
	}
	
//	private Class<?> clazz = this.getClass();
	private static final Log log = org.apache.commons.logging.LogFactory.getLog(JsonUtil.class);

	public JsonUtil() {
		// EnhancerByCGLIB
		this.excludeFieldNames.add("hibernateLazyInitializer");
		this.excludeFieldNames.add("interceptFieldCallback");
		// Property '" + key + "' of " + bean.getClass() + " has no read method. SKIPPED
		this.excludeFieldNames.add("latelyNames");
		this.excludeFieldNames.add("leader");
		this.excludeFieldNames.add("myLeader");
		// com.landray.kmss.sys.right.interfaces.ExtendAuthModel
		String[] extendAuthModelFieldNames = new String[] { "attachmentForms", "authAllEditors", "authAllReaders",
				"authArea", "authAttCopys", "authAttDownloads", "authAttNocopy", "authAttNodownload", "authAttNoprint",
				"authAttPrints", "authChangeAtt", "authChangeEditorFlag", "authChangeReaderFlag", "authEditorFlag",
				"authEditors", "authOtherEditors", "authOtherReaders", "authRBPFlag", "authReaderFlag", "authReaders",
				"customPropMap", "dynamicMap", "mechanismMap", "sysDictModel", "toFormPropertyMap" };
		this.excludeFieldNames.addAll(Arrays.asList(extendAuthModelFieldNames));
		// com.landray.kmss.sys.organization.model.SysOrgElement
//		this.excludeFieldNames.add("fdChildren");
//		this.excludeFieldNames.add("fdParent");
		String[] sysOrgElementFieldNames = new String[] { "addressTypeList", "allLeader", "allMyLeader",
				"authElementAdmins", "fdGroups", "fdInitPassword", "fdParentOrg", "fdPassword", "fdPersons", "fdPosts",
				"hbmChildren", "hbmGroups", "hbmParent", "hbmParentOrg", "hbmPersons", "hbmPosts", "hbmSuperLeader",
				"hbmSuperLeaderChildren", "hbmThisLeader", "hbmThisLeaderChildren", "docCreator", "docAlteror" };
		this.excludeFieldNames.addAll(Arrays.asList(sysOrgElementFieldNames));
		// com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel
		String[] sysSimpleCategoryAuthTmpModelFieldNames = new String[] { "authNotReaderFlag", "authTmpAttCopys",
				"authTmpAttDownloads", "authTmpAttNocopy", "authTmpAttNocopy", "authTmpAttNodownload",
				"authTmpAttNoprint", "authTmpAttPrints", "authTmpEditors", "authTmpReaders" };
		this.excludeFieldNames.addAll(Arrays.asList(sysSimpleCategoryAuthTmpModelFieldNames));
		// list - main
//		this.excludeFieldNames.add("docMain");
//		this.excludeFieldNames.add("docTemplate");
		this.excludeFieldNames.add("extendDataModelInfo");
		this.excludeFieldNames.add("extendDataXML");
		// fdParent - fdChildren
//		this.excludeFieldNames.add("fdChildren");
	}
	
	public JSONObject parse(Object bean) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "handler", "hibernateLazyInitializer", "interceptFieldCallback" });
//		jsonConfig.setIgnoreDefaultExcludes(false); // ����Ĭ�Ϻ���
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//		jsonConfig.setIgnoreTransientFields(false);
		return JSONObject.fromObject(bean, jsonConfig);
	}
	
	/**
	 * JavaBeanת��ΪJSONObject
	 */
	public JSONObject beanToJson(Object bean) {
		final JsonUtil jsonUtil = this;
		
		ClassCustomizer<SysOrgElement> classCustomizer = new ClassCustomizer<com.landray.kmss.sys.organization.model.SysOrgElement>() {
			@Override
			public net.sf.json.JSONObject getValue(com.landray.kmss.sys.organization.model.SysOrgElement object) {
				// TODO Auto-generated method stub
				net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
				jsonObject.put("fdId", object.getFdId());
				jsonObject.put("fdName", object.getFdName());
				jsonObject.put("fdNo", object.getFdNo());
				if (object.getFdParent() != null) {
					Object value = jsonUtil.getValue(object.getFdParent(), jsonUtil.getRecursionTimes(), this);
					jsonObject.put("fdParent", value);
				}
				return jsonObject;
			}
		};
		
		return beanToJson(bean, new ClassCustomizer<com.landray.kmss.sys.organization.model.SysOrgElement>() {
			@Override
			public JSONObject getValue(com.landray.kmss.sys.organization.model.SysOrgElement object) {
				// TODO Auto-generated method stub
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("fdId", object.getFdId());
				jsonObject.put("fdName", object.getFdName());
				jsonObject.put("fdNo", object.getFdNo());
				if (object.getFdParent() != null) {
					Object value = jsonUtil.getValue(object.getFdParent(), jsonUtil.getRecursionTimes(), this);
					jsonObject.put("fdParent", value);
				}
				return jsonObject;
			}
		});
//		return beanToJson(bean, null);
	}

	public <T> JSONObject beanToJson(Object bean, ClassCustomizer<T> classCustomizer) {
		if (bean == null) return null;

//		if (classCustomizer != null) {
//			// interface
//			Type[] genericInterfaces = classCustomizer.getClass().getGenericInterfaces();
//			if (genericInterfaces.length > 0) {
//				Type type = genericInterfaces[0];
//				if (type instanceof ParameterizedType) {
//					ParameterizedType parameterizedType = (ParameterizedType) type;
//					Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//					if (actualTypeArguments.length > 0) {
//						Type actualTypeArgument = actualTypeArguments[0];
//						if (actualTypeArgument instanceof Class) {
//							this.clazz = (Class<?>) actualTypeArgument;
//						}
//					}
//				}
//			}
//		}
		if (classCustomizer == null) {
			classCustomizer = new ClassCustomizer<T>() {
				@Override
				public JSONObject getValue(T object) {
					return null;
				}
			};
			classCustomizer.clazz = classCustomizer.getClass();
		}
		// class
		this.excludeFieldNames.add("class");
		this.excludeFieldNames.add("formClass");

//		JSONObject jsonObject = new JSONObject();
//		PropertyDescriptor[] propertyDescriptors = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(bean);
//		for (PropertyDescriptor propertyDescriptor: propertyDescriptors) {
//			this.oneBreadthIsRecursion = false;
//			this.setJsonObject(jsonObject, bean, propertyDescriptor, this.recursionTimes);
//			// ��ǰ����ݹ����
//			if(this.oneBreadthIsRecursion) {
//				this.oneDepthAllRecursionObject.clear();
//			}
//		}
//		return jsonObject;

		for (int i = 0; i <= this.recursionTimes; i++) {
			HashMap<Integer, Object> hashMap = new HashMap<>();
			this.recursionObject.add(hashMap);
		}
		// ��ʼ����ʼ�ձ������ٵݹ���ö�����ظ����ݼ���json��
		HashMap<Integer, Object> hashMap = new HashMap<>();
		hashMap.put(bean.hashCode(), bean);
		this.recursionObject.add(hashMap);

		return parseBean(bean, this.recursionTimes, classCustomizer);
	}

	/**
	 * ����JavaBean
	 */
	public JSONObject parseBean(Object bean, int recursionTimes, ClassCustomizer<?> classCustomizer) {
//		this.oneBreadthIsRecursion = true;
		JSONObject jsonObject = new JSONObject();

		java.beans.PropertyDescriptor[] propertyDescriptors = 
				org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(bean);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			this.setJsonObject(jsonObject, propertyDescriptor, bean, recursionTimes, classCustomizer);
		}
		return jsonObject;
	}
	
	private void setJsonObject(JSONObject jsonObject, PropertyDescriptor propertyDescriptor,
			Object bean, int recursionTimes, ClassCustomizer<?> classCustomizer) {
		String name = propertyDescriptor.getName();
		// ͨ����ϣ�������ܹ����ٵض�����Ԫ�ؽ��ж�λ��hash�㷨�������ٳ�ͻʹ�����Ⱦ����̣ܶ�����״̬��ʱ�临�Ӷ�ΪO(1)
		if (!this.excludeFieldNames.contains(name)) {
			Method readMethod = propertyDescriptor.getReadMethod();
			if (readMethod != null) {
				Object property = null;
				try {
					property = org.apache.commons.beanutils.PropertyUtils.getProperty(bean, name);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Object value = this.getValue(property, recursionTimes, classCustomizer);
				// keyΪnullʱ��net.sf.json.JSONObject�޷����룬�޸�Ϊ"null"��valueΪnullʱ���޸�Ϊ����Ȼ��ŵ�jsonObject
				jsonObject.put(name == null ? "null" : name, value == null ? "" : value);
			} else {
				this.excludeFieldNames.add(name);
				log.warn("Property '" + name + "' of " + bean.getClass() + " has no read method. SKIPPED");
			}
		}
	}

	/**
	 * ��ȡ�ֶε�ֵ
	 * @param object �ֶζ���
	 * @param recursionTimes �ݹ����
	 */
	public <T> Object getValue(Object object, int recursionTimes, ClassCustomizer<T> classCustomizer) {
		// JSONObject�з���Map��ʱ�򣬻��Զ���Map������JSONObject������JSON allows only string to be a key
//		if(recursionTimes <= 0)
//			return object.getClass();
//		--recursionTimes;
		Object value;
		if (object == null || object instanceof String || object instanceof Number || object instanceof Boolean) {
			value = object;
		} else if (object instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(this.dateFormat);
			value = sdf.format(object);
		} else if (Collection.class.isAssignableFrom(object.getClass())) {
			JSONArray jsonArray = new JSONArray();
			
			Collection<?> collection = (Collection<?>) object;
			for (Object next : collection) {
				// ���ƹ�����󼯺�
//            	Class<?> superclass = next.getClass().getSuperclass();
//            	Integer times = this.recursionClassTimes.get(superclass.getName());
//        		if(times != null) 
//        			--recursionTimes;
				Object val = this.getValue(next, recursionTimes, classCustomizer);
				jsonArray.add(val);
			}
			value = jsonArray;
		} else if (Map.class.isAssignableFrom(object.getClass())) {
			JSONObject jsonObject = new JSONObject();
			Map<?, ?> map = ((Map<?, ?>) object);
			try {
				for (Object key : map.keySet()) {
					Object obj = map.get(key);
					Object val = this.getValue(obj, recursionTimes, classCustomizer);

					jsonObject.put(key == null ? "null" : key.toString(), val == null ? "" : val);
				}
			} catch (java.lang.UnsupportedOperationException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			value = jsonObject;
		} else if (object.getClass().isArray()) {
			JSONArray jsonArray = new JSONArray();
			for (int i = 0; i < Array.getLength(object); i++) {
				Object obj = Array.get(object, i);
				Object val = this.getValue(obj, recursionTimes, classCustomizer);
				jsonArray.add(val);
			}
			value = jsonArray;
		} else {
			if (recursionTimes <= 0) return object.getClass();

			// �����ܴ�������
			if (classCustomizer.clazz.isInstance(object)) {
//			if (this.clazz.isInstance(object)) {
				try {
					return classCustomizer.getValue((T)object);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// ����õݹ�����е����ж���
			for (int i = recursionTimes; i > 0; i--) {
				HashMap<Integer, Object> hashMap = this.recursionObject.get(i);
				if (hashMap.isEmpty()) {
					break;
				} else {
					hashMap.clear();
				}
			}

			// ���������ǰ����Ĺ�ϵ
			int hashCode = object.hashCode();
			for (int i = recursionTimes + 1; i <= this.recursionTimes + 1; i++) {
				HashMap<Integer, Object> hashMap = this.recursionObject.get(i);
				// �ȱȽϹ�ϣ��
				Object oldObject = hashMap.get(hashCode);
				if (oldObject != null) {
					/*
					 * �˷���ֻ�Ƚ�public�ֶΡ�
					 * ���Ƚ�transient�ֶΣ���Ϊ���ǲ��ܱ����л���
					 * ���⣬�˷������Ƚ�static�ֶΣ���Ϊ���ǲ��Ƕ���ʵ����һ���֡�
					 * ���ĳ���ֶ���һ������/Map/Collection����Ƚ����ݣ������Ƕ�������á�
					 * @param excludeFields  ���Ƚϵ��ֶ�
					 */
					if (org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals(oldObject, object)) {
					//if (this.isEquals(oldObject, object)) {
						// ����BaseModel����
						if (com.landray.kmss.common.model.BaseModel.class.isAssignableFrom(object.getClass())) {
							String fdId = ((com.landray.kmss.common.model.BaseModel) object).getFdId();
							JSONObject json = new JSONObject();
							json.put("fdId", fdId);
							return json;
						}
						// ��ͬ���󷵻�null
						return null;
					} else {
						log.warn(object.getClass() + "��" + object.toString() + " �ֶβ���ȣ�");
					}
				}
			}
			HashMap<Integer, Object> hashMap = this.recursionObject.get(recursionTimes);
			hashMap.put(hashCode, object);

			// �ٴν������ҵݹ������һ
			// JSONObject objectValue = getMethodValue(object, --recursionTimes);
			value = this.parseBean(object, --recursionTimes, classCustomizer);
		}
		return value;
	}
	
	public void setRecursionTimes(int recursionTimes) {
		this.recursionTimes = recursionTimes;
	}
	
	public int getRecursionTimes() {
		return recursionTimes;
	}

	public void addExcludeFieldNames(List<String> excludeFieldNames) {
		this.excludeFieldNames.addAll(excludeFieldNames);
	}

	public boolean removeExcludeFieldNames(String fieldName) {
		return excludeFieldNames.remove(fieldName);
	}

	public void clearExcludeField() {
		excludeFieldNames.clear();
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/*class old {
		public <T> boolean isEquals(Object oldObject, Object newObject, String... ignoreProperties) {
			boolean isEquals = true;
			if (oldObject != null && newObject != null) {
				// ���û����дequals���������ж����������ڴ��ַ�Ƿ�һ�¼�oldObject==newObject
				if (oldObject.equals(newObject)) {
					return true;
				}

				Map<String, Object> oldObjectPropertyMap = this.getObjectPropertyMap(oldObject);
				Map<String, Object> newObjectPropertyMap = this.getObjectPropertyMap(newObject);

				for (String key : oldObjectPropertyMap.keySet()) {
					Object oldObjectPropertyValue = oldObjectPropertyMap.get(key);
					Object newObjectPropertyValue = newObjectPropertyMap.get(key);
					if (newObjectPropertyValue != null && oldObjectPropertyValue != null) {
						if (!(oldObjectPropertyValue.equals(newObjectPropertyValue))) {
							if (Collection.class.isAssignableFrom(oldObjectPropertyValue.getClass())) {

							} else if (com.landray.kmss.common.model.BaseModel.class
									.isAssignableFrom(oldObjectPropertyValue.getClass())) {

							} else {
								isEquals = false;
								break;
							}
						}
						// ��������ָ��null
					} else if (oldObjectPropertyValue != newObjectPropertyValue) {
						isEquals = false;
						break;
					}
				}
			} else if (oldObject != newObject) {
				isEquals = false;
			}
			return isEquals;
		}

		// ��ȡ�����������ֺ���ֵ
		public <T> Map<String, Object> getObjectPropertyMap(Object object, String... ignoreProperties) {
			Class<?> objectClass = object.getClass();
			PropertyDescriptor[] propertyDescriptors = org.apache.commons.beanutils.PropertyUtils.getPropertyDescriptors(objectClass);

			HashMap<String, Object> propertyMap = new HashMap<>(propertyDescriptors.length);

			Collection<?> ignorePropertyCollection = null;
			if (ignoreProperties != null) {
				ignorePropertyCollection = Arrays.asList(ignoreProperties);
			} else {
//				ignorePropertyCollection = this.excludeFieldNames;
			}
			if (ignorePropertyCollection.isEmpty()) {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					String name = propertyDescriptor.getName();
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null) {
						Object invoke = null;
						try {
							invoke = readMethod.invoke(object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						propertyMap.put(name, invoke);
					}
				}
			} else {
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					String name = propertyDescriptor.getName();
					Method readMethod = propertyDescriptor.getReadMethod();
					if (readMethod != null && ignorePropertyCollection.contains(name)) {
						Object invoke = null;
						try {
							invoke = readMethod.invoke(object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
						}
						propertyMap.put(name, invoke);
					}
				}
			}
			return propertyMap;
		}

		@SuppressWarnings("unused")
		private JSONObject parseForDeclaredField(Object bean, int recursionTimes) {
			JSONObject jsonObject = new JSONObject();
			// ͨ��getDeclaredFields()?����ȡ�������е��������ԣ���˽�У�
			java.lang.reflect.Field[] fields = bean.getClass().getDeclaredFields();

			for (java.lang.reflect.Field field : fields) {
				// ��������ͨ���������˽�б���
				// if (Modifier.isPrivate(field.getModifiers()))
				field.setAccessible(true);
				// ��ȡ�ֶ���������
				String name = field.getName();
				Object object = null;
				try {
					object = field.get(bean);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				if (object != null && name != null && name != "") {
//					Object value = getValue(object, recursionTimes);
//					jsonObject.put(name, value);
				} else {
					// ����ûֵ������ǰ��undefined
					jsonObject.put(name, "");
				}
			}
			return jsonObject;
		}

		@SuppressWarnings("unused")
		private JSONObject getMethodValue(Object object, int recursionTimes) {
			JSONObject jsonObject = new JSONObject();

			Method[] methods = object.getClass().getMethods();
			for (Method method : methods) {
				String name = method.getName();
				Class<?>[] parameterTypes = method.getParameterTypes();
				if (name.contains("getFd") && parameterTypes.length == 0) {
					Object invoke = null;
					try {
						invoke = method.invoke(object);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					if (invoke != null && !(invoke instanceof Method)) {
						int indexOf = name.indexOf("getF");
						if (indexOf != -1) {
							String substring = name.substring(indexOf + "getF".length(), name.length());
							if (substring != null && substring != "") {
//								Object value = getValue(invoke, recursionTimes);
//								jsonObject.put('f' + substring, value);
							}
						}
					}
				}
			}
			return jsonObject;
		}

		@SuppressWarnings("unused")
		private JSONObject getCGLIBValue(Object bean1) {
			JSONObject jsonObj = new JSONObject();
			Field[] fields = bean1.getClass().getDeclaredFields();
			for (java.lang.reflect.Field field : fields) {
				field.setAccessible(true);
				Object object1 = null;
				try {
					object1 = field.get(bean1);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				String name1 = field.getName();
				if (object1 != null && name1 != null && name1 != "") {
					if (object1 instanceof Method) {
						// �������֯�ܹ��������get����ȡֵ
						if (name1.contains("getFd")) {
							Method method = (Method) object1;
							Class<?>[] parameterTypes = method.getParameterTypes();
							// û�в�������
							if (parameterTypes.length == 0) {
								Object invoke = null;
								try {
									invoke = method.invoke(bean1);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
								if (invoke != null && !(invoke instanceof Method)) {
									int indexOf = name1.indexOf("CGLIB$getF");
									int lastIndexOf = name1.lastIndexOf("$");
									if (indexOf != -1 && lastIndexOf != -1 && lastIndexOf > indexOf) {
										String substring = name1.substring(indexOf + "CGLIB$getF".length(), lastIndexOf);
										if (substring != null && substring != "") {
											jsonObj.put('f' + substring, invoke.toString());
										}
									}
								}
							}
						}
					} else {

					}
				} else {
					jsonObj.put(name1, "");
				}
			}
			return jsonObj;
		}
	}*/
}
