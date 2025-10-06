package com.custodia.supply.request.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.custodia.supply.request.dto.RequestRow;
import com.custodia.supply.request.entity.Request;
//Request: filas para la tabla del detalle
public interface IRequestDao extends PagingAndSortingRepository<Request, Long>, CrudRepository<Request, Long> {
	
	/* === Brings only the request items by User id ===
	 * Allows to have empty fields by validating from RequestRow DTO
	 * Helps to have no assigned attributes from CustomerAccount when User first register.
	 */
	 @Query(value = """
			      select new com.custodia.supply.request.dto.RequestRow(
			        r.id,
			        r.description,
			        r.additionalItems,             
			        r.createAt,
			        s.address,
			        s.externalCode,
			        count(ri),
			        coalesce(sum(ri.quantity), 0)
			      )
			      from Request r
			      left join r.shipTo s
			      left join r.requests ri      
			      where r.user.id = :userId
			      group by r.id, r.description, r.additionalItems, r.createAt, s.address, s.externalCode
			      order by r.createAt desc
			    """,
			    countQuery = """
			      select count(distinct r.id)
			      from Request r
			      where r.user.id = :userId
			    """
			  )
			  Page<RequestRow> findRowsByUserId(@Param("userId") Long userId, Pageable pageable);
	 
	 // No use lazy on request view - request/view/userId - Only one consult
//	 @Query("select r from Request r join fetch r.user u join fetch r.requests l join fetch l.supplyItem where r.id=?1")
	 @Query("select distinct r from Request r"
	 		+ " join fetch r.user "
	 		+ "left join fetch r.requests ri "
	 		+ "left join fetch ri.supplyItem si "
	 		+ "left join fetch si.product p "
	 		+ "join fetch r.shipTo cs "
	 		+ "join fetch cs.customer ca "
	 		+ "where r.id = :id")
	 public Request fetchByIdWithUserWithRequestItemWithSupplyItem(Long id);
	 

	 		
}
