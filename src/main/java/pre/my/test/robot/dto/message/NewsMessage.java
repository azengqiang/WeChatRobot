package pre.my.test.robot.dto.message;

import java.util.List;

/**
 * 图文信息数据实体
 */
public class NewsMessage extends Message{
	private int ArticleCount;
	private List<News> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<News> getArticles() {
		return Articles;
	}
	public void setArticles(List<News> articles) {
		Articles = articles;
	}
}
