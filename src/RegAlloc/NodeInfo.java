package RegAlloc;

public class NodeInfo {
	java.util.Set in = new java.util.HashSet(); //��ָ��ǰ�Ļ��Ա���
	java.util.Set out = new java.util.HashSet(); //��ָ���Ļ��Ա��� �� ����Ծ����
	java.util.Set use = new java.util.HashSet(); //ĳָ��ʹ�õı��� - ��ֵ���ұ�
	java.util.Set def = new java.util.HashSet(); //ĳָ���ı��� - ��ֵ�����
}
