package PingYouGouPojo;

import java.io.Serializable;
import java.util.List;

import cn.pinyougou.pojo.TbSpecification;
import cn.pinyougou.pojo.TbSpecificationOption;

public class SpecificationPojo implements Serializable{
	
		private TbSpecification spification;
		private List<TbSpecificationOption> option;
		
		public SpecificationPojo() {
		}
		public TbSpecification getSpification() {
			return spification;
		}
		public void setSpification(TbSpecification spification) {
			this.spification = spification;
		}
		public List<TbSpecificationOption> getOption() {
			return option;
		}
		public void setOption(List<TbSpecificationOption> option) {
			this.option = option;
		}
		public SpecificationPojo(TbSpecification spification, List<TbSpecificationOption> option) {
			super();
			this.spification = spification;
			this.option = option;
		}
		
		
		
}
