本demo是演示listview展示多种类型的item的通用方法
主要实现方法：
通过实现BaseAdapter中getViewTypeCount()和getItemViewType(),
前者用于返回类型的个数，后者用于返回类型的种类，通常用Integer类型的数据代替